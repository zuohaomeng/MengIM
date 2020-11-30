package com.meng.mengim.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.client.handler.HeardBeatHandler;
import com.meng.mengim.client.service.AckRedisService;
import com.meng.mengim.client.service.IMClientService;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.util.MessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.apache.tomcat.util.http.fileupload.UploadContext;
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
    ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(300);
    @Resource
    private Channel channel;
    @Resource
    private AckRedisService ackRedisService;
    @Resource
    private HeardBeatHandler heardBeatHandler;

    @Override
    public void sendChatMessage(long memberId, String context) {

        MessageRequest request = MessageUtil.buildChatMessage(memberId, context);
        ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8);
        //发送
        channel.writeAndFlush(byteBuf);
        //重发校验
        resendCheck(channel, request);
    }

    @Override
    public void sendLoginMessage(long memberId) {
        MessageRequest request = MessageUtil.buildLoginMessage(memberId);
        ByteBuf buf = Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8);
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
        //ack缓存
        ackRedisService.saveMessageId(request.getId());
        //ack校验
        threadPool.schedule(() -> {
            doResendCheck(channel, request);
        }, 5, TimeUnit.SECONDS);
    }

    private void doResendCheck(Channel channel, MessageRequest request) {
        int time = 1;
        //重发两次
        while (time <= 2) {
            if (ackRedisService.exist(request.getId())) {
                LOGGER.info("[doResendCheck]-send message again.message={}", request);
                channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(request), CharsetUtil.UTF_8));
                try {
                    TimeUnit.SECONDS.sleep(2);
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
