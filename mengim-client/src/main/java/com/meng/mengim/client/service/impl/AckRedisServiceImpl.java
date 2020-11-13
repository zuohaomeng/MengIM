package com.meng.mengim.client.service.impl;

import com.meng.mengim.client.service.AckRedisService;
import com.meng.mengim.common.bean.BaseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
@Service
public class AckRedisServiceImpl implements AckRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存消息发送id10s
     */
    @Override
    public void saveMessageId(String messageId, BaseMessage message) {
        redisTemplate.opsForValue().set(messageId, message, 30, TimeUnit.SECONDS);
    }

    @Override
    public void deleteMessageId(String messageId) {
        redisTemplate.delete(messageId);
    }

    @Override
    public BaseMessage getMessageId(String messageId) {
        return (BaseMessage) redisTemplate.opsForValue().get(messageId);
    }
}
