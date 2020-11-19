package com.meng.mengim.server.handler.factory;

import com.meng.mengim.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author ZuoHao
 * @Date 2020/11/12
 * @Description
 */
@Component
public class HandlerFactory implements ApplicationContextAware, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerFactory.class);

    private ApplicationContext applicationContext ;

    private Map<Short, AbstractHandler> handlerMap = new HashMap<>();

    public AbstractHandler get(Short type){
        return handlerMap.get(type);
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, AbstractHandler> beans = applicationContext.getBeansOfType(AbstractHandler.class);
        beans.forEach((key, value) -> handlerMap.put(value.getType(), value));
    }
}
