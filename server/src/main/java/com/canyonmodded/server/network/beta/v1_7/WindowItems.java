package com.canyonmodded.server.network.beta.v1_7;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

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
