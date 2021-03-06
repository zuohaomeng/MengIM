package com.meng.mengim.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.client.handler.HeardBeatHandler;
import com.meng.mengim.client.service.AckStoreService;
import com.meng.mengim.client.service.IMClientService;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.util.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ZuoHao
 * @Date 2020/11/13
 * @Description
 */
@Service
public class IMClientServiceImpl implements IMClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IMClientServiceImpl.class);
    ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(1);
    @Resource
    private Channel channel;
    @Resource(name = "ackStoreServiceImpl")
    private AckStoreService ackStoreService;
    @Resource
    private HeardBeatHandler heardBeatHandler;

    @Override
    public void sendChatMessage(long memberId, long receivedId, String context) {
        MessageRequest request = MessageUtil.buildChatMessage(memberId, receivedId, context);
        ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8);
        //ack缓存
        ackStoreService.saveMessageId(request.getId());
        //发送
        channel.writeAndFlush(byteBuf);
        //重发校验
        resendCheck(channel, request);
    }

    @Override
    public void sendLoginMessage(long memberId) {
        MessageRequest request = MessageUtil.buildLoginMessage(memberId);
        ByteBuf buf = Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8);
        //ack缓存
        ackStoreService.saveMessageId(request.getId());
        //发送
        channel.writeAndFlush(buf);
        //重发校验
        resendCheck(channel, request);
        heardBeatHandler.heardBead(channel, memberId);
    }


    /**
     * 重发确认
     */
    private void resendCheck(Channel channel, MessageRequest request) {
        //ack校验
        threadPool.schedule(() -> {
            doResendCheck(channel, request);
        }, 10, TimeUnit.SECONDS);
    }

    private void doResendCheck(Channel channel, MessageRequest request) {
        int time = 1;
        //重发两次
        while (time <= 2) {
            if (ackStoreService.exist(request.getId())) {
                LOGGER.error("[doResendCheck]-send message again.message={}", request);
                channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8));
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
            time++;
        }
        LOGGER.error("[doResendCheck],send message error,times=3,message={}", request);
    }
}
