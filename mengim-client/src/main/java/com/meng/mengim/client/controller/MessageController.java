package com.meng.mengim.client.controller;

import com.meng.mengim.client.service.IMClientService;
import com.meng.mengim.common.bean.ChatMessage;
import com.meng.mengim.common.bean.LoginMessage;
import com.meng.mengim.common.constant.MessageType;
import com.meng.mengim.common.util.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:05
 * @Description 用户接入层
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private IMClientService IMClientService;

    @Resource
    private Channel channel;

    @RequestMapping("/sendChatMessage")
    public Object sendChatMessage() {
        IMClientService.sendChatMessage(178183852, "你好");
        return "success";
    }

    @RequestMapping("/sendLoginMessage")
    public Object sendLoginMessage() {
        IMClientService.sendLoginMessage(178183852);
        return "success";
    }

    @RequestMapping("/test")
    public Object test() {
        return "success";
    }

}
