package com.meng.mengim.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.client.service.AckRedisService;
import com.meng.mengim.client.service.MessageClientService;
import com.meng.mengim.common.bean.BaseMessage;
import com.meng.mengim.common.constant.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
@Service
public class MessageClientServiceImpl implements MessageClientService {
    ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(300);
    @Resource
    private Channel channel;
    @Resource
    private AckRedisService ackRedisService;

    @Override
    public void sendChatMessage(long memberId, String context) {

        BaseMessage message = MessageUtil.buildChatMessage(memberId, context);
        ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(message), CharsetUtil.UTF_8);
        //写入
        channel.writeAndFlush(byteBuf);
        //ack缓存
        ackRedisService.saveMessageId(message.getId(), message);
        //ack校验
        threadPool.schedule(() -> {
            if (channel.isActive()) {
                checkAndResend(channel, message);
            }
        }, 5, TimeUnit.SECONDS);
    }

    private void checkAndResend(Channel channel, BaseMessage message) {

    }

    @Override
    public void sendLoginMessage(long message) {

    }
}
