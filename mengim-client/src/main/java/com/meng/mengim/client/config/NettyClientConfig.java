package com.meng.mengim.client.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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

    private final String host = "127.0.0.1";
    private final int port = 9999;

    private ChannelFuture channelFuture;
    private Channel channel;

    @Resource
    private MessageClientHandler messageClientHandler;



    @PostConstruct
    public void init() throws Exception {
        LOGGER.info("[NettyClientConfig],init-------");
        //用于处理客户端连接和socketchannel的网络读写
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(messageClientHandler);
                        }
                    });
            channelFuture = b.connect().sync();
            channel = channelFuture.channel();

        }catch (Exception e){
            LOGGER.error("[NettyClientConfig init],init error");
        }

    }

    @Bean
    public Channel channel(){
        return channel;
    }
}
