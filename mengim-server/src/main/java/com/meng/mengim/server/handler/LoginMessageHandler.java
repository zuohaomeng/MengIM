package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.bean.LoginMessage;
import com.meng.mengim.common.constant.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description 上线消息处理
 */
@Component
public class LoginMessageHandler extends AbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginMessageHandler.class);
    private static final ConcurrentHashMap<Long, Channel> userChannel = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Channel, Long> channelUser = new ConcurrentHashMap<>();

    @Override
    public Short getType() {
        return MessageType.LOGIN_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext context, MessageRequest message) {
        LoginMessage loginMessage = JSON.parseObject(message.getBody(), LoginMessage.class);
        userChannel.put(loginMessage.getMemberId(), context.channel());
        channelUser.put(context.channel(), loginMessage.getMemberId());
        System.out.println(loginMessage.toString());
    }

    public void removeUserChannel(ChannelHandlerContext context) {
        Long memberId = channelUser.remove(context.channel());
        if(memberId != null){
            userChannel.remove(memberId);
            logger.info("[removeUserChannel],remove User and Channel,memberId={}",memberId);
        }
    }

}
