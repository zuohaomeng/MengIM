package com.meng.mengim.client.service;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
public interface MessageClientService {
    /**
     * 发送聊天消息
     */
    void sendChatMessage(long messageId,String context);
    /**
     * 发送上线消息
     */
    void sendLoginMessage(long message);
}
