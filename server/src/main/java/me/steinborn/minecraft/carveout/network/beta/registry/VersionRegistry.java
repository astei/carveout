package me.steinborn.minecraft.carveout.network.beta.registry;

import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;

public class VersionRegistry {
    private final IntObjectMap<ProtocolRegistry> protocolVersions;

    public VersionRegistry() {
        this.protocolVersions = new IntObjectHashMap<>();
    }

    public ProtocolRegistry getProtocolRegistry(int protocolVersion) {
        return protocolVersions.get(protocolVersion);
    }

    public ProtocolRegistry createRegistry(int protocolVersion) {
        ProtocolRegistry r = protocolVersions.get(protocolVersion);
        if (r == null) {
            r = new ProtocolRegistry(protocolVersion);
            protocolVersions.put(protocolVersion, r);
            return r;
        }
        return r;
    }
}
