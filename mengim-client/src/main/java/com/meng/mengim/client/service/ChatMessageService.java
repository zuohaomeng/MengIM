package com.meng.mengim.client.service;

import com.meng.mengim.common.bean.ChatMessage;

/**
 * @Author ZuoHao
 * @Date 2020/11/30
 * @Description
 */
public interface ChatMessageService {


    /**
     * 接收消息-显示
     * @param chatMessage
     */
    void received(ChatMessage chatMessage);
}
