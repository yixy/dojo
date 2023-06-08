package me.youzhilane.dojo.service;

import me.youzhilane.dojo.spring.BeanPostProcessor;
import me.youzhilane.dojo.spring.annotation.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public void postProcessBeforeInitialization(String beanName, Object bean) {
        System.out.println(beanName+": postProcessBeforeInitialization");
    }

    @Override
    public void postProcessAfterInitialization(String beanName, Object bean) {
        System.out.println(beanName+": postProcessAfterInitialization");
    }
}
