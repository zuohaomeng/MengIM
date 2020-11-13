package com.meng.mengim.common.util;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.MessageRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author ZuoHao
 * @Date 2020/11/11
 * @Description
 */
public class JsonUtils {

    public static ByteBuf buildBaseMessage(short type, Object msg) {
        MessageRequest message = new MessageRequest();
        message.setType(type);
        message.setId(UUID.randomUUID().toString());
        String body = JSON.toJSONString(msg);
        message.setBody(body);
        String json = JSON.toJSONString(message);
        return Unpooled.copiedBuffer(json, CharsetUtil.UTF_8);
    }
}
