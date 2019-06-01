package com.canyonmodded.server.network.beta.v1_7;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

public class PlayerPositionLook implements MinecraftPacket {
    private double x;
    private double y;
    private double stance;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PlayerPositionLook() {
    }

    public PlayerPositionLook(double x, double y, double stance, double z, float yaw, float pitch, boolean onGround) {
        this.x = x;
        this.y = y;
        this.stance = stance;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public int guessPacketLength() {
        return 42;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.x = buf.readDouble();
        if (direction == Direction.TO_SERVER) {
            this.y = buf.readDouble();
            this.stance = buf.readDouble();
        } else {
            this.stance = buf.readDouble();
            this.y = buf.readDouble();
        }
        this.z = buf.readDouble();
        yaw = buf.readFloat();
        pitch = buf.readFloat();
        onGround = buf.readBoolean();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeDouble(x);
        if (direction == Direction.TO_SERVER) {
            buf.writeDouble(y);
            buf.writeDouble(stance);
        } else {
            buf.writeDouble(stance);
            buf.writeDouble(y);
        }
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeBoolean(onGround);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "PlayerPositionLook{" +
                "x=" + x +
                ", y=" + y +
                ", stance=" + stance +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                '}';
    }
}
