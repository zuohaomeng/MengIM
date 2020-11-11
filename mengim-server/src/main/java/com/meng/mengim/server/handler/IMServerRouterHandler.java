package com.meng.mengim.server.handler;

import com.meng.mengim.common.bean.BaseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:14
 * @Description    netty转发器
 */
@ChannelHandler.Sharable//标示一个ChannelHandler可以被多个Channel安全地共享
public class IMServerRouterHandler extends SimpleChannelInboundHandler<ByteBuf>{
    private final Logger logger = LoggerFactory.getLogger(IMServerRouterHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf message) throws Exception {
        System.out.println(message.toString(CharsetUtil.UTF_8));
        logger.info("接收信息成功！");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
