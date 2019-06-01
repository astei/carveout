package com.canyonmodded.server.network.beta.v1_7;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

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
