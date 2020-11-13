package com.meng.mengim.common.constant;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.BaseMessage;
import com.meng.mengim.common.bean.ChatMessage;

import java.util.UUID;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
public class MessageUtil {
    public static BaseMessage buildChatMessage(long messageId, String context){
        BaseMessage baseMessage = new BaseMessage();
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
