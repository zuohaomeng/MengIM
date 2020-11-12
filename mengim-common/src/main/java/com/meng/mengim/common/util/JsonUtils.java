package com.meng.mengim.common.util;

import com.alibaba.fastjson.JSON;
import com.meng.mengim.common.bean.BaseMessage;
import com.meng.mengim.common.bean.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Author ZuoHao
 * @Date 2020/11/11
 * @Description
 */
public class JsonUtils {


    public static ByteBuf buildByteBuf(short type, Object msg) {
        BaseMessage message = new BaseMessage();
        message.setType(type);
        String body = JSON.toJSONString(msg);
        message.setBody(body);
        String json = JSON.toJSONString(message);
        return Unpooled.copiedBuffer(json, CharsetUtil.UTF_8);
    }
}
