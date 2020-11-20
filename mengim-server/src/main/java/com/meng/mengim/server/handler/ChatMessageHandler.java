package com.meng.mengim.server.handler;

import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description   普通聊天消息逻辑处理
 */
@Component
public class ChatMessageHandler extends AbstractHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageHandler.class);
    @Override
    public Short getType() {
        return MessageType.CHAT_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext ctx, MessageRequest msgRequest) {
        LOGGER.info("普通聊天消息处理开始了----");
    }
}
