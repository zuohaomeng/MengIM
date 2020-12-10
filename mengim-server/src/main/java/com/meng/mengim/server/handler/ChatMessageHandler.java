package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.ChatMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import com.meng.mengim.server.service.UserChannelService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description 普通聊天消息逻辑处理
 */
@Component
public class ChatMessageHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    @Resource
    private UserChannelService userChannelService;

    @Override
    public Short getType() {
        return MessageType.CHAT_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext ctx, MessageRequest msgRequest) {
        ChatMessage chatMessage = JSON.parseObject(msgRequest.getBody(), ChatMessage.class);
        Channel channel = userChannelService.getChannelByMemberId(chatMessage.getReceivedId());
        //说明在线
        logger.info("[ChatMessageHandler handler]-received message,message={}", chatMessage);
        if (channel != null) {
            ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(msgRequest), CharsetUtil.UTF_8);
            channel.writeAndFlush(byteBuf);
        } else {//不在线，缓存
            System.out.println("缓存");
        }

    }
}
