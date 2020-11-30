package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.HeartMessage;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.constant.MessageType;
import com.meng.mengim.server.service.UserChannelService;
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
    private UserChannelService userChannelService;


    @Override
    public Short getType() {
        return MessageType.HEART_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext ctx, MessageRequest request) {
        HeartMessage message = JSON.parseObject(request.getBody(), HeartMessage.class);
        //如果没有代表重新连接
        if (userChannelService.getChannelByMemberId(message.getMemberId()) == null) {
            userChannelService.addUserChannel(message.getMemberId(), ctx.channel());
        }
    }
}
