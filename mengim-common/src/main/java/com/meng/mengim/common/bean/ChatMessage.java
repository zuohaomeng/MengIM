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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "memberId=" + memberId +
                ", content=" + content +
                '}';
    }
}
