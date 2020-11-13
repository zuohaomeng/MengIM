package com.meng.mengim.common.util;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.bean.ChatMessage;
import com.meng.mengim.common.constant.MessageType;

import java.util.UUID;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
public class MessageUtil {
    public static MessageRequest buildChatMessage(long messageId, String context){
        MessageRequest baseMessage = new MessageRequest();
        baseMessage.setType(MessageType.CHAT_MESSAGE.getType());
        baseMessage.setId(UUID.randomUUID().toString());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(context);
        chatMessage.setMemberId(messageId);
        String body = JSON.toJSONString(chatMessage);
        baseMessage.setBody(body);
        return baseMessage;
    }

}
