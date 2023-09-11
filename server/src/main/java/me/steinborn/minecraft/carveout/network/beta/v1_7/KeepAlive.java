package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class KeepAlive implements MinecraftPacket {
    public static final KeepAlive INSTANCE = new KeepAlive();

    private KeepAlive() {

    }

    @Override
    public int guessPacketLength() {
        return 0;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {

    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {

    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }
}
