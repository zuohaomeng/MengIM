package com.meng.mengim.server.handler;

import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * @Author ZuoHao
 * @Date 2020/11/18
 * @Description  心跳消息处理类
 */
@Component
public class HeartBeatMessageImpl extends AbstractHandler{
    @Override
    public Short getType() {
        return MessageType.HEART_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext context, MessageRequest message) {
    }
}
