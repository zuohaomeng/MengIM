package com.meng.mengim.client.service.impl;

import com.meng.mengim.client.service.AckRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
@Service
public class AckRedisServiceImpl implements AckRedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存消息发送id10s
     */
    @Override
    public void saveMessageId(String messageId) {
        redisTemplate.opsForValue().set(messageId, 1, 30, TimeUnit.SECONDS);
    }

    @Override
    public void deleteMessageId(String messageId) {
        redisTemplate.delete(messageId);
    }

    @Override
    public boolean exist(String messageId) {
        return redisTemplate.hasKey(messageId);
    }
}
