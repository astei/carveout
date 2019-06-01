package com.canyonmodded.server.network.beta.handler;

import com.canyonmodded.server.level.CanyonChunk;
import com.canyonmodded.server.network.beta.common.Login;
import com.canyonmodded.server.network.beta.v1_7.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlaySessionHandler implements MinecraftBetaSessionHandler {
    private final MinecraftBetaConnection connection;
    private ScheduledFuture<?> pingTask;
    private long lastKeepAlive = -1;
    private boolean spawned = false;

    public PlaySessionHandler(MinecraftBetaConnection connection) {
        this.connection = connection;
    }

    @Override
    public void activated() {
        connection.delayedWrite(new Login(1, "", 0, (byte) 0));

        // test
        connection.delayedWrite(new WindowItems());
        connection.delayedWrite(new SpawnPosition(0, 65, 0));
        //connection.delayedWrite(new PlayerPositionLook(0, 65, 65 + 1.62, 0, 0, 0, false));

        List<CanyonChunk> testChunks = new ArrayList<>();

        for (int x = -7; x <= 7; x++) {
            for (int z = -7; z <= 7; z++) {
                CanyonChunk cc = new CanyonChunk(x, z);
                cc.makeFakeChunk();
                testChunks.add(cc);
            }
        }
        testChunks.sort(new ChunkAroundComparator());

        for (CanyonChunk chunk : testChunks) {
            connection.delayedWrite(new PreChunk(chunk.getX(), chunk.getZ(), true));
            connection.delayedWrite(chunk.createChunkPacket());
        }

        connection.delayedWrite(new PlayerPositionLook(0.5, 65, 65 + 1.62, 0.5, 0, 0, false));
        connection.delayedWrite(new Chat("Hello, Minecraft Beta 1.7.3!"));
        connection.flush();

        // Ping task
        pingTask = connection.eventLoop().scheduleAtFixedRate(() -> {
            connection.write(KeepAlive.INSTANCE);
            lastKeepAlive = System.currentTimeMillis();
        }, 0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void disconnected() {
        if (pingTask != null) {
            pingTask.cancel(false);
        }
    }

    @Override
    public void handle(PlayerPositionLook playerPositionLook) {
        System.out.println("PPL: " + playerPositionLook);
    }

    @Override
    public void handle(PlayerPosition playerPosition) {
        System.out.println("PP: " + playerPosition);
    }

    @Override
    public void handle(PlayerLook playerLook) {
        System.out.println("PL: " + playerLook);
    }

    @Override
    public void handle(Chat chat) {
        connection.write(new Chat("Repeating after you: " + chat.getMessage()));
    }

    private static class ChunkAroundComparator implements Comparator<CanyonChunk> {

        @Override
        public int compare(CanyonChunk o1, CanyonChunk o2) {
            // Use whichever is closest to the origin.
            return Integer.compare(distance(o1.getX(), o1.getZ()), distance(o2.getX(), o2.getZ()));
        }

        private int distance(int x, int z) {
            int dx = 0 - x;
            int dz = 0 - z;
            return dx * dx + dz * dz;
        }
    }
}
