package com.canyonmodded.server.network.beta.registry;

import com.canyonmodded.server.network.MinecraftBetaBuffer;
import com.canyonmodded.server.network.MinecraftPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.collection.ByteObjectHashMap;
import io.netty.util.collection.ByteObjectMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ProtocolRegistry {
    private final int protocolVersion;
    private final ByteObjectMap<Supplier<? extends MinecraftPacket>> idToSuppliers;
    private final Map<Class<? extends MinecraftPacket>, Byte> packetToId;

    ProtocolRegistry(int protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.idToSuppliers = new ByteObjectHashMap<>();
        this.packetToId = new HashMap<>();
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public <T extends MinecraftPacket> ProtocolRegistry register(byte id, Class<T> pkt, Supplier<T> supplier) {
        idToSuppliers.put(id, supplier);
        packetToId.put(pkt, id);
        return this;
    }

    public MinecraftPacket supply(byte id) {
        Supplier<? extends MinecraftPacket> supplier = idToSuppliers.get(id);
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown packet id " + Integer.toHexString(id));
    }

    public void serialize(MinecraftPacket packet, ByteBuf buf) {
        Byte id = packetToId.get(packet.getClass());
        if (id == null) {
            throw new IllegalArgumentException("No packet id registered for " + packet.getClass().getName());
        }
        buf.writeByte(id);
        packet.encode(new MinecraftBetaBuffer(buf), protocolVersion, MinecraftPacket.Direction.TO_CLIENT);
    }
}
