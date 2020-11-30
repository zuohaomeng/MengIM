package com.meng.mengim.server.handler;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import com.meng.mengim.common.bean.LoginMessage;
import com.meng.mengim.common.constant.AttributeKeyConstant;
import com.meng.mengim.common.constant.MessageType;
import com.meng.mengim.server.service.UserChannelService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description 上线消息处理
 */
@Component
public class LoginMessageHandler extends AbstractHandler {
    private final Logger logger = LoggerFactory.getLogger(LoginMessageHandler.class);

    @Resource
    private UserChannelService userChannelService;

    @Override
    public Short getType() {
        return MessageType.LOGIN_MESSAGE.getType();
    }

    @Override
    public void handler(ChannelHandlerContext ctx, MessageRequest message) {
        LoginMessage loginMessage = JSON.parseObject(message.getBody(), LoginMessage.class);
        userChannelService.addUserChannel(loginMessage.getMemberId(), ctx.channel());

        ctx.channel().attr(AttributeKeyConstant.MEMBER_ID).set(String.valueOf(loginMessage.getMemberId()));

        System.out.println(loginMessage.getMemberId() + "登录了");
    }



}
