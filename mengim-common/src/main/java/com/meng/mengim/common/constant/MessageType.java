package com.meng.mengim.common.constant;

/**
 * @Author ZuoHao
 * @Date 2020/11/11
 * @Description
 */
public enum MessageType {
    /**
     * 普通文本消息
     */
    CHAT_MESSAGE((short) 1, "普通文本消息"),
    /**
     * 心跳消息
     */
    HEART_MESSAGE((short)2,"心跳消息"),
    /**
     * 登录消息
     */
    LOGIN_MESSAGE((short)3,"登录消息"),
    /**
     * ack消息
     */
    ACK_MESSAGE((short) 4,"ack消息"),
    ;
    private short type;
    private String desc;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    MessageType(short type, String desc) {
        this.type = type;
        this.desc = desc;
    }


    public static MessageType of(short type) {
        for (MessageType t : MessageType.values()) {
            if (t.getType() == type) {
                return t;
            }
        }
        return null;
    }
}
