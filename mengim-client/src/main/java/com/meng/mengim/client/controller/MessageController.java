package com.meng.mengim.client.controller;

import com.meng.mengim.client.service.IMClientService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private IMClientService IMClientService;

    @Resource
    private Channel channel;


    @RequestMapping("/sendChatMessage")
    public Object sendChatMessage() {
        IMClientService.sendChatMessage(123456, 456789, "你好");
        return "success";
    }

    @RequestMapping("/sendLoginMessage")
    public Object sendLoginMessage(@RequestParam long messageId) {
        IMClientService.sendLoginMessage(messageId);
        return "success";
    }

    @RequestMapping("/test")
    public Object test() {
        return "success";
    }

}
