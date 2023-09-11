package me.steinborn.minecraft.carveout.network.beta;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.registry.BetaPackets;
import me.steinborn.minecraft.carveout.network.beta.registry.ProtocolRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MinecraftBetaDecoder extends ByteToMessageDecoder {
    private ProtocolRegistry registry;

    public MinecraftBetaDecoder() {
        super();
        this.registry = BetaPackets.generic();
    }

    public void setRegistry(ProtocolRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // The beta protocol doesn't include a "length" field. Therefore, the best way to parse a message is to attempt
        // to decode a message immediately and if we couldn't decode, then try again later. This sucks, but we didn't
        // get to call the shots.
        //
        // To account for this, we allow recovering from `IndexOutOfBoundsException` by allowing the `ByteToMessageDecoder`
        // to cumulate more data.
        if (!in.isReadable()) {
            return;
        }

        int ri = in.readableBytes();
        byte packetId = in.readByte();

        MinecraftPacket pkt = registry.supply(packetId);

        int guessLength = pkt.guessPacketLength();
        if (guessLength >= 0) {
            // If length is known and enough data is readable, then we can do a straight decode.
            if (in.isReadable(guessLength)) {
                pkt.decode(new MinecraftBetaBuffer(in), registry.getProtocolVersion(), MinecraftPacket.Direction.TO_SERVER);
                out.add(pkt);
            } else {
                in.readerIndex(ri);
            }
        } else {
            // Otherwise, the length is undetermined and we need to try repeatedly decoding until a result is found.
            try {
                pkt.decode(new MinecraftBetaBuffer(in), registry.getProtocolVersion(), MinecraftPacket.Direction.TO_SERVER);
                out.add(pkt);
            } catch (IndexOutOfBoundsException e) {
                // There is no reasonable way to recover from this. Just reset the buffer and proceed.
                System.out.println("/!\\ INCOMPLETE READ! " + in.writerIndex());
                in.readerIndex(ri);
            }
        }
    }
}
