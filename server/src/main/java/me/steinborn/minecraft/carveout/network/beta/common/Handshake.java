package me.steinborn.minecraft.carveout.network.beta.common;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

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
