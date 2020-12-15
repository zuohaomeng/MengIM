package com.meng.mengim.client.service.impl;

import com.meng.mengim.client.service.AckRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(AckRedisServiceImpl.class);
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static final String BEGIN = "ACK:";
    /**
     * 保存消息发送id30s
     */
    @Override
    public void saveMessageId(String messageId) {
        redisTemplate.opsForValue().set(BEGIN+messageId, 1, 30, TimeUnit.SECONDS);
    }

    @Override
    public Boolean deleteMessageId(String messageId) {
        return redisTemplate.delete(BEGIN+messageId);
    }

    @Override
    public Boolean exist(String messageId) {
        return redisTemplate.hasKey(BEGIN+messageId);
    }
}
