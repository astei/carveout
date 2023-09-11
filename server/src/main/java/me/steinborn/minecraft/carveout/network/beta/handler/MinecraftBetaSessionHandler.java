package me.steinborn.minecraft.carveout.network.beta.handler;

import me.steinborn.minecraft.carveout.network.beta.common.Disconnect;
import me.steinborn.minecraft.carveout.network.beta.common.Handshake;
import me.steinborn.minecraft.carveout.network.beta.common.Login;
import me.steinborn.minecraft.carveout.network.beta.v1_7.*;
import me.steinborn.minecraft.carveout.network.beta.v1_7.*;

public interface MinecraftBetaSessionHandler {
    default void activated() {

    }

    default void deactivated() {

    }

    default void connected() {

    }

    default void disconnected() {

    }

    default void exception(Throwable throwable) {

    }

    default void handle(Handshake handshake) {

    }

    default void handle(Login request) {

    }

    default void handle(Disconnect disconnect) {

    }

    default void handle(KeepAlive keepAlive) {

    }

    default void handle(PreChunk preChunk) {

    }

    default void handle(PlayerPositionLook playerPositionLook) {

    }

    default void handle(PlayerPosition playerPosition) {

    }

    default void handle(PlayerLook playerLook) {

    }

    default void handle(PlayerPacket playerPacket) {

    }

    default void handle(Chat chat) {

    }
}
