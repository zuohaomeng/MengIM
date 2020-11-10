package com.meng.mengim.server.handler;

import com.meng.mengim.common.bean.BaseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:14
 * @Description    netty转发器
 */
//标示一个ChannelHandler可以被多个Channel安全地共享
@ChannelHandler.Sharable
public class IMServerRouterHandler extends SimpleChannelInboundHandler<BaseMessage>{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage baseMessage) throws Exception {

    }
}
