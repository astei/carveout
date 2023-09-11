package me.steinborn.minecraft.carveout.network.beta.handler;

import me.steinborn.minecraft.carveout.network.beta.common.Disconnect;
import me.steinborn.minecraft.carveout.network.beta.common.Handshake;
import me.steinborn.minecraft.carveout.network.beta.common.Login;
import me.steinborn.minecraft.carveout.network.beta.registry.BetaPackets;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;

public class HandshakeSessionHandler implements MinecraftBetaSessionHandler {
    private HandshakeState state;
    private @Nullable String username;
    private final MinecraftBetaConnection connection;

    public HandshakeSessionHandler(MinecraftBetaConnection connection) {
        this.connection = connection;
        this.state = HandshakeState.CONNECTED;
    }

    @Override
    public void handle(Handshake handshake) {
        // The first packet in the login process is the `Handshake` packet.
        Preconditions.checkState(this.state == HandshakeState.CONNECTED, "Already got Handshake");

        this.username = handshake.getUsername();
        this.state = HandshakeState.RECEIVED_HANDSHAKE;

        // This packet means we'll continue in offline-mode.
        connection.write(new Handshake("-"));
    }

    @Override
    public void handle(Login request) {
        Preconditions.checkState(this.state == HandshakeState.RECEIVED_HANDSHAKE, "Needed Login");
        this.state = HandshakeState.RECEIVED_LOGINREQUEST;

        if (this.username == null) {
            // Should already have this but let's check for sanity.
            throw new AssertionError();
        }

        if (!BetaPackets.isSupported(request.getProtocolVersion())) {
            connection.closeWith(new Disconnect("Your version of Minecraft isn't supported."));
            return;
        }

        connection.setProtocolVersion(request.getProtocolVersion());
        connection.setSessionHandler(new PlaySessionHandler(connection));
    }

    private enum HandshakeState {
        CONNECTED,
        RECEIVED_HANDSHAKE,
        RECEIVED_LOGINREQUEST
    }
}
