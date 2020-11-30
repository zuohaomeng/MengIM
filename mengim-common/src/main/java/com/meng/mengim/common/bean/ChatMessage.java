package com.meng.mengim.common.bean;

/**
 * @Author ZuoHao
 * @Date 2020/11/11
 * @Description
 */
public class ChatMessage {
    /**
     * 用户
     */
    private long memberId;
    /**
     * 接收消息的用户
     */
    private long receivedId;
    /**
     * 内容
     */
    private String content;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(long receivedId) {
        this.receivedId = receivedId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "memberId=" + memberId +
                ", receivedId=" + receivedId +
                ", content='" + content + '\'' +
                '}';
    }
}
