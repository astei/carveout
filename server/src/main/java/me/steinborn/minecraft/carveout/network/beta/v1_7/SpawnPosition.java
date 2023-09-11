package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class SpawnPosition implements MinecraftPacket {
    private int x;
    private int y;
    private int z;

    public SpawnPosition() {
    }

    public SpawnPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "SpawnPosition{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
