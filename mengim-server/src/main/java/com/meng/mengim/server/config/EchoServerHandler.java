package com.meng.mengim.server.config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 梦醉
 * @date 2019/12/28--21:30
 */
//标示一个ChannelHandler可以被多个Channel安全地共享
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    //这些方法都可以挂钩在事件生命周期的恰当点上


    //处理所有接受到的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        //将消息记录到控制台
        System.out.println("Server received: "+in.toString(CharsetUtil.UTF_8));
        //将接受到的消息写给发送者，而不传冲刷出站消息
        ctx.writeAndFlush(in);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }
}
