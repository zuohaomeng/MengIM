package com.meng.mengim.server.handler;


import com.meng.mengim.common.bean.BaseMessage;

/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description  各种消息处理父类
 */
public abstract class AbstractHandler{
    /**
     * 返回消息类型
     * @return
     */
    public abstract Short getType();

    /**
     * 具体处理逻辑，进行重载
     */
    public abstract void handler(BaseMessage message);
}
