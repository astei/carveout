package me.steinborn.minecraft.carveout.network.beta;

import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.registry.BetaPackets;
import me.steinborn.minecraft.carveout.network.beta.registry.ProtocolRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MinecraftBetaEncoder extends MessageToByteEncoder<MinecraftPacket> {
    private ProtocolRegistry registry;

    public MinecraftBetaEncoder() {
        super();
        this.registry = BetaPackets.generic();
    }

    public void setRegistry(ProtocolRegistry registry) {
        this.registry = registry;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MinecraftPacket msg, ByteBuf out) throws Exception {
        this.registry.serialize(msg, out);
    }
}
