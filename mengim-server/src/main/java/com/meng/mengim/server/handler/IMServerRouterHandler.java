package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.AckMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.AttributeKeyConstant;
import com.meng.mengim.common.constant.MessageType;
import com.meng.mengim.common.util.JsonUtils;
import com.meng.mengim.server.handler.factory.HandlerFactory;
import com.meng.mengim.server.service.UserChannelService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:14
 * @Description netty转发器
 * 标示一个ChannelHandler可以被多个Channel安全地共享
 */
@ChannelHandler.Sharable
@Component
public class IMServerRouterHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final Logger LOGGER = LoggerFactory.getLogger(IMServerRouterHandler.class);
    @Resource
    private HandlerFactory handlerFactory;
    @Resource
    private UserChannelService userChannelService;

    @Override
    protected void channelRead0(ChannelHandlerContext context, ByteBuf buf) throws Exception {
        String str = buf.toString(CharsetUtil.UTF_8);
        MessageRequest message = JSON.parseObject(str, MessageRequest.class);
        //获取具体的处理类型
        AbstractHandler handler = handlerFactory.get(message.getType());

        //进行处理
        try {
            handler.handler(context, message);
        } catch (Exception e) {
            LOGGER.error("[具体消息处理失败],error=", e);
        }
        //如果不是ack消息，发回ack应答
        if (MessageType.isNeedAck(message.getType())) {
            returnAckMessage(context, message.getId());
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                userChannelService.removeUserChannel(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    private void returnAckMessage(ChannelHandlerContext context, String messageId) {
        AckMessage ackMessage = new AckMessage(messageId);
        ByteBuf messageBuf = JsonUtils.buildMessageRequest(MessageType.ACK_MESSAGE.getType(), ackMessage);
        context.writeAndFlush(messageBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("[exceptionCaught],disconnect,memberId={}", ctx.channel().attr(AttributeKeyConstant.MEMBER_ID).get());
        System.out.println("出问题了");
        userChannelService.removeUserChannel(ctx);
        cause.printStackTrace();
        ctx.close();
    }
}
