package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class PlayerLook implements MinecraftPacket {
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PlayerLook() {
    }

    public PlayerLook(float yaw, float pitch, boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
        this.onGround = buf.readBoolean();
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeFloat(this.yaw);
        buf.writeFloat(this.pitch);
        buf.writeBoolean(this.onGround);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "PlayerLook{" +
                "yaw=" + yaw +
                ", pitch=" + pitch +
                ", onGround=" + onGround +
                '}';
    }
}
