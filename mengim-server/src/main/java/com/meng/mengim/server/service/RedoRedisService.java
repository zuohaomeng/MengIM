package com.meng.mengim.server.service;

/**
 * @author ZuoHao
 * @date 2020/12/15
 */
public interface RedoRedisService {
    /**
     * 是否重复消费,true代表重复消费
     * @param id
     * @return
     */
    boolean redo(String id);
}
