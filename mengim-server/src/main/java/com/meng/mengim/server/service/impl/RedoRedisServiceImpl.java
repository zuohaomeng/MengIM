package com.meng.mengim.server.service.impl;

import com.meng.mengim.server.service.RedoRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ZuoHao
 * @date 2020/12/15
 */
@Service
public class RedoRedisServiceImpl implements RedoRedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String BEGIN = "redo";

    private static final long time = 60;

    @Override
    public boolean redo(String id) {
        try {
            Boolean hasKey = redisTemplate.hasKey(BEGIN + id);
            if (hasKey == null || hasKey) {
                return true;
            }
            redisTemplate.opsForValue().set(BEGIN + id, 1, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }
}
