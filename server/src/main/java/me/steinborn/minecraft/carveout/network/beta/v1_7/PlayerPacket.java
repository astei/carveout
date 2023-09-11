package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class PlayerPacket implements MinecraftPacket {
    private boolean onGround;

    @Override
    public int guessPacketLength() {
        return 1;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.onGround = buf.readBoolean();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }
}
