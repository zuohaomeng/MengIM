package com.meng.mengim.server.service.impl;

import com.meng.mengim.server.service.UserChannelService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ZuoHao
 * @Date 2020/11/30
 * @Description
 */
@Service
public class UserChannelServiceImpl implements UserChannelService {
    private final Logger logger = LoggerFactory.getLogger(UserChannelServiceImpl.class);
    private static final ConcurrentHashMap<Long, Channel> userChannel = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Channel, Long> channelUser = new ConcurrentHashMap<>();

    @Override
    public void addUserChannel(long memberId, Channel channel) {
        userChannel.put(memberId, channel);
        channelUser.put(channel, memberId);
    }

    @Override
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

    @Override
    public Channel getChannelByMemberId(long memberId) {
        return userChannel.get(memberId);
    }
}
