package com.meng.mengim.server.config;

import com.meng.mengim.server.handler.IMServerRouterHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

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


    @PostConstruct
    public void init() {
        LOGGER.info("[NettyConfig],init-------");
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            serverBootstrap.group(group)
                    //指定所使用NIO的传输Channel
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //添加一个EchoServerHandler到子Channel的ChannelPipeline
                    //每次channel被注册到channelpipeline是，调用这个方法
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        //初始化每一个channel
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //因为EchoServerHandler被标注未@Shareable,所以我们可以总是使用同样的实例
                            ch.pipeline().addLast(new IMServerRouterHandler());
                        }
                    });
            //同步地绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture f = serverBootstrap.bind().sync();
        }catch (Exception e){
            LOGGER.error("[NettyConfig init error],",e);
        }
    }
}