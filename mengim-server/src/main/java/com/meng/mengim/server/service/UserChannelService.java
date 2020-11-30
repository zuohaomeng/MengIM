package com.meng.mengim.server.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author ZuoHao
 * @Date 2020/11/30
 * @Description
 */
public interface UserChannelService {

    /**
     * 添加用户id和channel缓存
     * @param memberId
     * @param channel
     */
    void addUserChannel(long memberId, Channel channel);

    /**
     * 移除用户id和channel缓存
     * @param ctx
     */
    void removeUserChannel(ChannelHandlerContext ctx);

    /**
     * 根据用户id获取Channel
     * @param memberId
     * @return
     */
    Channel getChannelByMemberId(long memberId);

}
