package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;
import io.netty.buffer.ByteBufUtil;

public class MapChunk implements MinecraftPacket {
    private int blockX;
    private short blockY;
    private int blockZ;
    private byte sizeX;
    private byte sizeY;
    private byte sizeZ;
    private byte[] chunk;

    public MapChunk(int blockX, short blockY, int blockZ, byte sizeX, byte sizeY, byte sizeZ, byte[] chunk) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.chunk = chunk;
    }

    public MapChunk() {
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        // encode-only
        throw new UnsupportedOperationException();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeInt(blockX);
        buf.writeShort(blockY);
        buf.writeInt(blockZ);
        buf.writeByte(sizeX);
        buf.writeByte(sizeY);
        buf.writeByte(sizeZ);
        buf.writeInt(chunk.length);
        buf.writeBytes(chunk);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        // encode-only
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "MapChunk{" +
                "blockX=" + blockX +
                ", blockY=" + blockY +
                ", blockZ=" + blockZ +
                ", sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", sizeZ=" + sizeZ +
                ", chunk=" + ByteBufUtil.hexDump(chunk) +
                '}';
    }
}
