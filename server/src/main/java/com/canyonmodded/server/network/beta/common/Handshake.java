package com.canyonmodded.server.network.beta.common;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

public class Handshake implements MinecraftPacket {
    private String username;

    public Handshake() {

    }

    public Handshake(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        username = buf.readUTF16String(16);
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeUTF16String(username);
    }

    @Override
    public String toString() {
        return "Handshake{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }
}
