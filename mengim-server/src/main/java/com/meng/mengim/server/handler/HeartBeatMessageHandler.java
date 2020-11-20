package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.HeartMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author ZuoHao
 * @Date 2020/11/18
 * @Description 心跳消息处理类
 */
@Component
public class HeartBeatMessageHandler extends AbstractHandler {
    @Resource
    private LoginMessageHandler loginMessageHandler;


    @Override
    public Short getType() {
        return MessageType.HEART_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext ctx, MessageRequest request) {
        HeartMessage message = JSON.parseObject(request.getBody(), HeartMessage.class);
        //如果没有代表重新连接
        if (loginMessageHandler.getChannelByMemberId(message.getMemberId()) == null) {
            loginMessageHandler.addUserChannel(message.getMemberId(), ctx.channel());
        }
    }
}
