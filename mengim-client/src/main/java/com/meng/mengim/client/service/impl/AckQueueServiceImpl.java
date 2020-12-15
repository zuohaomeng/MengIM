package com.meng.mengim.client.service.impl;

import com.meng.mengim.client.service.AckStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZuoHao
 * @date 2020/12/15
 */
@Service
public class AckQueueServiceImpl implements AckStoreService, ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(AckStoreServiceImpl.class);
    private final static LinkedHashMap<String, Long> ackQueue = new LinkedHashMap(1024);
    ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(1);

    @Override
    public void saveMessageId(String messageId) {
        ackQueue.put(messageId, System.currentTimeMillis());
    }

    @Override
    public Boolean deleteMessageId(String messageId) {
        Long result = ackQueue.remove(messageId);
        return result != null;
    }

    @Override
    public Boolean exist(String messageId) {
        return ackQueue.containsKey(messageId);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        threadPool.scheduleAtFixedRate(() -> {
            for (Iterator<Map.Entry<String, Long>> it = ackQueue.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Long> item = it.next();
                String key = item.getKey();
                it.remove();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }
}
