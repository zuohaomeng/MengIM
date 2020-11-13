package com.meng.mengim.common.bean;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description Ack消息类型
 */
public class AckMessage {
    /**
     * ack应答id,对应需要应答消息的唯一id
     */
    private String ackId;

    public AckMessage() {
    }

    public AckMessage(String ackId) {
        this.ackId = ackId;
    }

    public String getAckId() {
        return ackId;
    }

    public void setAckId(String ackId) {
        this.ackId = ackId;
    }

    @Override
    public String toString() {
        return "AckMessage{" +
                "ackId='" + ackId + '\'' +
                '}';
    }
}
