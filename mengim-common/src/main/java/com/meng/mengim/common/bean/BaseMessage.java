package com.meng.mengim.common.bean;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:28
 * @Description 基础消息类型
 */
public class BaseMessage {
    /**
     * 消息类型，心跳，普通消息，登录消息，ack
     */
    private short type;
    /**
     * 消息体
     */
    private ObjectMapper body;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public ObjectMapper getBody() {
        return body;
    }

    public void setBody(ObjectMapper body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "type=" + type +
                ", body=" + body +
                '}';
    }
}
