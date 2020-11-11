package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.BaseMessage;
import com.meng.mengim.common.bean.ChatMessage;
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
 * @Description netty转发器
 */
@ChannelHandler.Sharable//标示一个ChannelHandler可以被多个Channel安全地共享
public class IMServerRouterHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Logger LOGGER = LoggerFactory.getLogger(IMServerRouterHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf buf) throws Exception {
        String str = buf.toString(CharsetUtil.UTF_8);
        BaseMessage message = JSON.parseObject(str, BaseMessage.class);
        LOGGER.info("[channelRead0]-[receive message],message={}",message);
        if(message.getType() == 1){
            ChatMessage chatMessage = JSON.parseObject(message.getBody(), ChatMessage.class);
            LOGGER.info("[channelRead0]-[receive chatMessage],message={}",chatMessage);
        }
        //将消息记录到控制台
        System.out.println("Server received: " + buf.toString(CharsetUtil.UTF_8));
        LOGGER.info("接收信息成功！");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
