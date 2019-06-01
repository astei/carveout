package com.canyonmodded.server.network.beta.handler;

import com.canyonmodded.server.network.beta.common.Disconnect;
import com.canyonmodded.server.network.beta.common.Handshake;
import com.canyonmodded.server.network.beta.common.Login;
import com.canyonmodded.server.network.beta.v1_7.*;

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
