package com.meng.mengim.common.bean;


/**
 * @Author ZuoHao
 * @Date 2020/11/10 18:28
 * @Description 基础消息类型
 */
public class BaseMessage {
    /**
     * 消息唯一id
     */
    private String id;
    /**
     * 消息类型，心跳，普通消息，登录消息，ack
     */
    private short type;
    /**
     * 消息体
     */
    private String body;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "type=" + type +
                ", id=" + id +
                ", body='" + body + '\'' +
                '}';
    }
}
