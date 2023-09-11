package me.steinborn.minecraft.carveout.network.beta.v1_7;

import me.steinborn.minecraft.carveout.network.MinecraftBetaBuffer;
import me.steinborn.minecraft.carveout.network.MinecraftPacket;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaSessionHandler;

public class Chat implements MinecraftPacket {
    private String message;

    public Chat() {
    }

    public Chat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        this.message = buf.readUTF16String(119);
    }

    @Override
    public void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction) {
        buf.writeUTF16String(message);
    }

    @Override
    public void handle(MinecraftBetaSessionHandler handler) {
        handler.handle(this);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "message='" + message + '\'' +
                '}';
    }
}
