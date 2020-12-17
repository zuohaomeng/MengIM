package com.meng.mengim.client.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.client.service.AckStoreService;
import com.meng.mengim.client.service.ChatMessageService;
import com.meng.mengim.common.bean.AckMessage;
import com.meng.mengim.common.bean.ChatMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author ZuoHao
 * @Date 2020/11/19
 * @Description
 */
@Service
public class MessageClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClientHandler.class);


    @Resource(name = "ackStoreServiceImpl")
    private AckStoreService ackStoreService;
    @Resource
    private ChatMessageService chatMessageService;

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

        //1.ACK确认
        if (msgRequest.getType() == MessageType.ACK_MESSAGE.getType()) {
            AckMessage ackMessage = JSON.parseObject(msgRequest.getBody(), AckMessage.class);
            Boolean result = ackStoreService.deleteMessageId(ackMessage.getAckId());
            if (!result) {
                LOGGER.error("[channelRead0]-ack,delete messageId error.messageId={}", ackMessage.getAckId());
            }
            return;
        }
        //2.业务逻辑处理
        handler(msgRequest);
    }

    void handler(MessageRequest msgRequest) {
        switch (Objects.requireNonNull(MessageType.of(msgRequest.getType()))) {
            case CHAT_MESSAGE:
                ChatMessage chatMessage = JSON.parseObject(msgRequest.getBody(), ChatMessage.class);
                chatMessageService.received(chatMessage);
                break;
            default:
                System.out.println(2);
        }
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
