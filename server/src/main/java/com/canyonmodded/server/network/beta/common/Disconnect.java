package com.canyonmodded.server.network.beta.common;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

public class Disconnect implements MinecraftPacket {
    private String message;

    public Disconnect() {

    }

    public Disconnect(String message) {
        this.message = message;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        message = buf.readUTF16String(256);
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeUTF16String(message);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Disconnect{" +
                "message='" + message + '\'' +
                '}';
    }
}
