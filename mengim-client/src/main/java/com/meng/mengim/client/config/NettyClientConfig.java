package com.meng.mengim.client.config;

import com.meng.mengim.client.handler.MessageClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 21:33
 * @Description
 */
@Configuration
public class NettyClientConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyClientConfig.class);

    private final static String host = "127.0.0.1";
    private final static int port = 9999;

    private ChannelFuture channelFuture;
    private Channel channel;

    @Resource
    private MessageClientHandler messageClientHandler;


    @PostConstruct
    public void init() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(messageClientHandler);
                        }
                    });
            channelFuture = b.connect().sync();
            channel = channelFuture.channel();

        } catch (Exception e) {
            LOGGER.error("[NettyClientConfig init],init error");
        }

    }

    @Bean
    public Channel channel() {
        return channel;
    }
}
