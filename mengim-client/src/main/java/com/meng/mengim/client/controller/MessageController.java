package com.meng.mengim.client.controller;

import com.meng.mengim.common.bean.BaseMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:05
 * @Description  用户接入层
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private Channel channel;

    @RequestMapping("/send")
    public Object send(){
        String message = "看海贼王";
        ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
        channelFuture.addListener( e -> LOGGER.info("客户端发消息成功={}", message));
        return "success";
    }
    @RequestMapping("/test")
    public Object test(){
        return "success";
    }

}
