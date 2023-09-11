package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class WindowItems implements MinecraftPacket {
    private byte windowId;

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeByte(windowId);
        buf.writeShort(45);
        for (int i = 0; i < 45; i++) {
            buf.writeShort(-1);
        }
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        throw new UnsupportedOperationException();
    }
}
