package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.bean.LoginMessage;
import com.meng.mengim.common.constant.AttributeKeyConstant;
import com.meng.mengim.common.constant.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
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
    public void handler(ChannelHandlerContext ctx, MessageRequest message) {
        LoginMessage loginMessage = JSON.parseObject(message.getBody(), LoginMessage.class);
        addUserChannel(loginMessage.getMemberId(), ctx.channel());

        ctx.channel().attr(AttributeKeyConstant.MEMBER_ID).set(String.valueOf(loginMessage.getMemberId()));

        System.out.println(loginMessage.getMemberId() + "登录了");
    }

    public void addUserChannel(long memberId, Channel channel) {
        userChannel.put(memberId, channel);
        channelUser.put(channel, memberId);
    }

    public void removeUserChannel(ChannelHandlerContext ctx) {
        Long memberId = channelUser.get(ctx.channel());
        //登录之后再移除
        if (memberId != null) {
            channelUser.remove(ctx.channel());
            userChannel.remove(memberId);
            //context.channel().close();
            logger.info("[removeUserChannel],remove User and Channel,memberId={}", memberId);
        }
    }

    /**
     * 根据用户id获取Channel
     *
     * @return
     */
    public Channel getChannelByMemberId(long memberId) {
        return userChannel.get(memberId);
    }

}
