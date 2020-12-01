package com.meng.mengim.client.service.impl;

import com.meng.mengim.client.service.ChatMessageService;
import com.meng.mengim.common.bean.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author ZuoHao
 * @Date 2020/11/30
 * @Description 消息处理
 */
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Override
    public void received(ChatMessage chatMessage) {
        String message = chatMessage.getMemberId() + "-" +
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "----------------------" +
                chatMessage.getContent();
        System.out.println(message);

    }
}
