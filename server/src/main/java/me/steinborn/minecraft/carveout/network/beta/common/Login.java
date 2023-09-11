package me.steinborn.minecraft.carveout.network.beta.common;

import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class Login implements MinecraftPacket {
    private int protocolVersion;
    private String username;
    private long mapSeed;
    private byte dimension;

    public Login() {
    }

    public Login(int protocolVersion, String username, long mapSeed, byte dimension) {
        this.protocolVersion = protocolVersion;
        this.username = username;
        this.mapSeed = mapSeed;
        this.dimension = dimension;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.protocolVersion = buf.readInt();
        this.username = buf.readUTF16String(16);
        this.mapSeed = buf.readLong();
        this.dimension = buf.readByte();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeInt(protocolVersion);
        buf.writeUTF16String(username);
        buf.writeLong(mapSeed);
        buf.writeByte(dimension);
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getUsername() {
        return username;
    }

    public long getMapSeed() {
        return mapSeed;
    }

    public byte getDimension() {
        return dimension;
    }

    @Override
    public String toString() {
        return "Login{" +
                "protocolVersion=" + protocolVersion +
                ", username='" + username + '\'' +
                ", mapSeed=" + mapSeed +
                ", dimension=" + dimension +
                '}';
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }
}
