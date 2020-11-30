package com.meng.mengim.client.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.util.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/19
 * @Description
 */
@Component
public class HeardBeatHandler {
    private final Logger logger = LoggerFactory.getLogger(HeardBeatHandler.class);
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    /**
     * 每10秒一次心跳轮训时间实现
     */
    public void heardBead(Channel channel, long memberId) {
        MessageRequest request = MessageUtil.buildHeardMessage(memberId);
        executorService.scheduleAtFixedRate(() -> {
            ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8);
            channel.writeAndFlush(byteBuf);
        }, 50000, 10, TimeUnit.SECONDS);
    }
}
