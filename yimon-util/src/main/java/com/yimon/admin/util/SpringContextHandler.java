package com.yimon.admin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: ym.gao
 * @description: spring上下文
 * @date: 2024/5/8 14:12
 */
@Component
public class SpringContextHandler implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringContextHandler() {
    }

    public static <T> T getBeanByName(String name, Class<T> tClass) {
        return context.getBean(name, tClass);
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
