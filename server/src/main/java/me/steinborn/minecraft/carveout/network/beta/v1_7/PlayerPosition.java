package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class PlayerPosition implements MinecraftPacket {
    private double x;
    private double y;
    private double stance;
    private double z;
    private boolean onGround;

    public PlayerPosition() {
    }

    public PlayerPosition(double x, double y, double stance, double z, boolean onGround) {
        this.x = x;
        this.y = y;
        this.stance = stance;
        this.z = z;
        this.onGround = onGround;
    }

    @Override
    public int guessPacketLength() {
        return 33;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.stance = buf.readDouble();
        this.z = buf.readDouble();
        this.onGround = buf.readBoolean();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.stance);
        buf.writeDouble(this.z);
        buf.writeBoolean(this.onGround);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "PlayerPosition{" +
                "x=" + x +
                ", y=" + y +
                ", stance=" + stance +
                ", z=" + z +
                ", onGround=" + onGround +
                '}';
    }
}
