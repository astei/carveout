package me.steinborn.minecraft.carveout.network.beta.registry;

import me.steinborn.minecraft.carveout.network.beta.common.Disconnect;
import me.steinborn.minecraft.carveout.network.beta.common.Handshake;
import me.steinborn.minecraft.carveout.network.beta.common.Login;
import me.steinborn.minecraft.carveout.network.beta.v1_7.*;
import me.steinborn.minecraft.carveout.network.beta.v1_7.*;

public class BetaPackets {
    public static final int GENERIC_VERSION = 0;
    public static final int MINECRAFT_BETA_1_7_3 = 14;

    public static final VersionRegistry VERSIONS = new VersionRegistry();

    static {
        // The generic protocol version only needs enough packets to get to the "login request".
        VERSIONS.createRegistry(GENERIC_VERSION)
                .register((byte) 0x02, Handshake.class, Handshake::new)
                .register((byte) 0x01, Login.class, Login::new)
                .register((byte) 0xff, Disconnect.class, Disconnect::new);

        // Beta 1.7.3
        VERSIONS.createRegistry(MINECRAFT_BETA_1_7_3)
                .register((byte) 0x00, KeepAlive.class, () -> KeepAlive.INSTANCE)
                .register((byte) 0x01, Login.class, Login::new)
                .register((byte) 0xff, Disconnect.class, Disconnect::new)
                .register((byte) 0x32, PreChunk.class, PreChunk::new)
                .register((byte) 0x0d, PlayerPositionLook.class, PlayerPositionLook::new)
                .register((byte) 0x0b, PlayerPosition.class, PlayerPosition::new)
                .register((byte) 0x0c, PlayerLook.class, PlayerLook::new)
                .register((byte) 0x33, MapChunk.class, MapChunk::new)
                .register((byte) 0x06, SpawnPosition.class, SpawnPosition::new)
                .register((byte) 0x68, WindowItems.class, WindowItems::new)
                .register((byte) 0x0a, PlayerPacket.class, PlayerPacket::new)
                .register((byte) 0x03, Chat.class, Chat::new);
    }

    public static ProtocolRegistry generic() {
        return VERSIONS.getProtocolRegistry(GENERIC_VERSION);
    }

    public static boolean isSupported(int version) {
        return VERSIONS.getProtocolRegistry(GENERIC_VERSION) != null;
    }
}
