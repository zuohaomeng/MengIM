package com.meng.mengim.client.config;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.client.service.AckRedisService;
import com.meng.mengim.client.service.IMClientService;
import com.meng.mengim.common.bean.AckMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MessageClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClientHandler.class);


    @Resource
    private IMClientService IMClientService;

    @Resource
    private AckRedisService ackRedisService;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    /**
     * 接受到消息处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        String str = msg.toString(CharsetUtil.UTF_8);
        MessageRequest msgRequest = JSON.parseObject(str, MessageRequest.class);
        LOGGER.info("[channelRead0],receive message,message={}", msgRequest);

        //1.ACK确认
        if (msgRequest.getType() == MessageType.ACK_MESSAGE.getType()) {
            AckMessage ackMessage = JSON.parseObject(msgRequest.getBody(), AckMessage.class);
            ackRedisService.deleteMessageId(ackMessage.getAckId());
        }
        //2.业务逻辑处理

    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //记录并关闭Channel
        cause.printStackTrace();
        ctx.close();
    }
}
