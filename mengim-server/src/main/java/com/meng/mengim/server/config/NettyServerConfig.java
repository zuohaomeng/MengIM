package com.meng.mengim.server.config;

import com.meng.mengim.server.handler.IMServerRouterHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 19:00
 * @Description
 */
@Configuration
public class NettyServerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerConfig.class);
    private static final int port = 9999;
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    @Autowired
    private IMServerRouterHandler imServerRouterHandler;

    @PostConstruct
    public void init() {
        LOGGER.info("[NettyConfig],init-------");
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        //初始化每一个channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS))
                                    .addLast(imServerRouterHandler);
                        }
                    });
            ChannelFuture f = serverBootstrap.bind().sync();
        } catch (Exception e) {
            LOGGER.error("[NettyConfig init error],", e);
        }
    }
}
