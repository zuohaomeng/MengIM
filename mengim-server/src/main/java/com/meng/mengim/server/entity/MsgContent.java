package com.meng.mengim.server.entity;


/**
 * 消息内容表
 * @author ZuoHao
 * @date 2020/12/16
 */
public class MsgContent {
    /**
     * 消息id
     */
    private int mid;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发送者
     */
    private long senderId;
    /**
     * 接收者
     */
    private long receiveId;
}
