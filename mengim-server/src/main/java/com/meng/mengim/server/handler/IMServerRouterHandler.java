package com.meng.mengim.server.handler;

import com.meng.mengim.common.bean.BaseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:14
 * @Description    netty转发器
 */
//标示一个ChannelHandler可以被多个Channel安全地共享
@ChannelHandler.Sharable
public class IMServerRouterHandler extends SimpleChannelInboundHandler<BaseMessage>{
    private final Logger logger = LoggerFactory.getLogger(IMServerRouterHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMessage message) throws Exception {

        System.out.println(message.getType());
        logger.info("接收信息成功！");
    }
}
