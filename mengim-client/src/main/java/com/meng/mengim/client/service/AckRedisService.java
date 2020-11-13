package com.meng.mengim.client.service;

import com.meng.mengim.common.bean.BaseMessage;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description  Ack应答消息处理逻辑
 */
public interface AckRedisService {
    /**
     * 保存发送的消息
     * @param messageId
     */
    void saveMessageId(String messageId, BaseMessage message);

    /**
     * 删除消息id
     * @param messageId
     */
    void deleteMessageId(String messageId);
    /**
     * 获取
     */
    BaseMessage getMessageId(String messageId);
}
