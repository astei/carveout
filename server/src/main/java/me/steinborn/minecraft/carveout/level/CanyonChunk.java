package me.steinborn.minecraft.carveout.level;

import me.steinborn.minecraft.carveout.level.util.NibbleArray;
import me.steinborn.minecraft.carveout.network.beta.v1_7.MapChunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;

/**
 * This class implements a world chunk.
 */
public class CanyonChunk {
    // TODO: Split into chunk sections so we don't need to allocate lots of memory for one chunk (80KB)
    private static final int FULL_CHUNK_SIZE = 16 * 128 * 16;

    private final int x;
    private final int z;
    private final byte[] blocks;
    private final NibbleArray metadata;
    private final NibbleArray blockLight;
    private final NibbleArray skyLight;

    /**
     * wireChunkCache is a cached copy of the wire version of the chunk. Setting world blocks will invalidate the cache.
     */
    private volatile byte[] wireChunkCache;

    public CanyonChunk(int x, int z) {
        this.x = x;
        this.z = z;
        this.blocks = new byte[FULL_CHUNK_SIZE]; // 1
        this.metadata = new NibbleArray(FULL_CHUNK_SIZE); // 1/2
        this.blockLight = new NibbleArray(FULL_CHUNK_SIZE); // 1/2
        this.skyLight = new NibbleArray(FULL_CHUNK_SIZE); // 1/2
    }

    private static int pos(int x, int y, int z) {
        return y + (z * 128) + (x * 128 * 16);
    }

    public void setBlock(int x, int y, int z, byte id) {
        int index = pos(x, y, z);

        blocks[index] = id;
        wireChunkCache = null;
    }

    public void makeFakeChunk() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                setBlock(x, 0, z, (byte) 7);
                for (int y = 1; y < 64; y++) {
                    setBlock(x, y, z, (byte) 3);
                }
                setBlock(x, 64, z, (byte) 2);
            }
        }
        recomputeSkylight();
    }

    private void recomputeSkylight() {
        skyLight.fill((byte) 0);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 127; y > 0; y--) {
                    int p = pos(x, y, z);
                    if (blocks[p] != 0) {
                        break;
                    }
                    skyLight.set(p, (byte) 15);
                }
            }
        }
    }

    public MapChunk createChunkPacket() {
        if (wireChunkCache == null) {
            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            try (OutputStream out = new DeflaterOutputStream(compressed)) {
                out.write(blocks);
                out.write(metadata.getData());
                out.write(blockLight.getData());
                out.write(skyLight.getData());
            } catch (IOException e) {
                throw new AssertionError(e);
            }
            wireChunkCache = compressed.toByteArray();
        }
        return new MapChunk(x << 4, (short) 0, z << 4, (byte) 15, (byte) 127, (byte) 15, wireChunkCache);
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
