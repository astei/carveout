package com.canyonmodded.server.network;

import com.canyonmodded.server.network.beta.handler.MinecraftBetaSessionHandler;

public interface MinecraftPacket {
    enum Direction {
        TO_CLIENT,
        TO_SERVER
    }

    /**
     * Attempts to give a "best-effort" guess for how large the incoming packet will be. This assists the
     * {@link com.canyonmodded.server.network.beta.MinecraftBetaDecoder} in framing the data correctly.
     * @return a best guess as to how large this packet will be, or -1 if one can't be made
     */
    default int guessPacketLength() {
        return -1;
    }

    /**
     * Attempts to decode the packet.
     * @param buf the buffer to decode from
     * @param protocolVersion the protocol version in use
     * @param direction the direction the packet is going to
     */
    void decode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction);

    /**
     * Encodes the packet.
     * @param buf the buffer to encode into
     * @param protocolVersion the protocol version in use
     * @param direction the direction the packet will go to
     */
    void encode(MinecraftBetaBuffer buf, int protocolVersion, Direction direction);

    void handle(MinecraftBetaSessionHandler handler);
}
