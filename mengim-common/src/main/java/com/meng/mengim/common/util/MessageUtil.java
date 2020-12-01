package com.meng.mengim.common.util;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.HeartMessage;
import com.meng.mengim.common.bean.LoginMessage;
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
    public static MessageRequest buildChatMessage(long memberId,long receivedId, String context){
        MessageRequest request = new MessageRequest();
        request.setType(MessageType.CHAT_MESSAGE.getType());
        request.setId(UUID.randomUUID().toString());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(context);
        chatMessage.setMemberId(memberId);
        chatMessage.setReceivedId(receivedId);
        String body = JSON.toJSONString(chatMessage);
        request.setBody(body);
        return request;
    }
    public static MessageRequest buildLoginMessage(long memberId){
        MessageRequest request = new MessageRequest();
        request.setType(MessageType.LOGIN_MESSAGE.getType());
        request.setId(UUID.randomUUID().toString());
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setMemberId(memberId);
        String body = JSON.toJSONString(loginMessage);
        request.setBody(body);
        return request;
    }
    public static MessageRequest buildHeardMessage(long memberId){
        MessageRequest request = new MessageRequest();
        request.setType(MessageType.HEART_MESSAGE.getType());
        HeartMessage heartMessage = new HeartMessage();
        heartMessage.setMemberId(memberId);
        String body = JSON.toJSONString(heartMessage);
        request.setBody(body);
        return request;
    }
}
