package me.steinborn.minecraft.carveout;

import me.steinborn.minecraft.carveout.network.beta.MinecraftBetaDecoder;
import me.steinborn.minecraft.carveout.network.beta.MinecraftBetaEncoder;
import me.steinborn.minecraft.carveout.network.beta.handler.HandshakeSessionHandler;
import me.steinborn.minecraft.carveout.network.beta.handler.MinecraftBetaConnection;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarveoutServer {
    private static final Logger logger = LogManager.getLogger(CarveoutServer.class);

    public static void main(String... args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast("beta-decoder", new MinecraftBetaDecoder());
                        ch.pipeline().addLast("beta-encoder", new MinecraftBetaEncoder());

                        MinecraftBetaConnection conn = new MinecraftBetaConnection(ch);
                        conn.setSessionHandler(new HandshakeSessionHandler(conn));
                        ch.pipeline().addLast("session-handler", conn);
                    }
                })
                .bind(25561)
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            logger.info("Listening on {}", future.channel().localAddress());
                        } else {
                            logger.error("Can't listen to port 25561", future.cause());
                        }
                    }
                });
    }
}
