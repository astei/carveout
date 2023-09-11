package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class PreChunk implements MinecraftPacket {
    private int x;
    private int z;
    private boolean load;

    public PreChunk() {
    }

    public PreChunk(int x, int z, boolean load) {
        this.x = x;
        this.z = z;
        this.load = load;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isLoad() {
        return load;
    }

    @Override
    public int guessPacketLength() {
        return 10;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.load = buf.readBoolean();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeInt(x);
        buf.writeInt(z);
        buf.writeBoolean(load);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "PreChunk{" +
                "x=" + x +
                ", z=" + z +
                ", load=" + load +
                '}';
    }
}
