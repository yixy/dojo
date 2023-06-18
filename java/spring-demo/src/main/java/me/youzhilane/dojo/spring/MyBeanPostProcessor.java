package me.youzhilane.dojo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor, InstantiationAwareBeanPostProcessor {
    //1. 在 Bean 实例化前执行的回调方法
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            System.out.println(beanName + ": InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation");
        }
        return null;
    }

    // 3. 在 Bean 实例化后执行的回调方法，可在这里对已经实例化的对象进行一些"梳妆打扮"；
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserService) {
            System.out.println(beanName + ": InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation");
        }
        return true;
    }

    //4. 在bean实例化后，属性注入前执行，它可以对bean属性进行修改或者替换。
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        if (bean instanceof UserService) {
            UserService userService=(UserService)bean;
            System.out.println(beanName
                    + ": InstantiationAwareBeanPostProcessor.postProcessProperties; -未设置属性值- "
                    + " userService.userInfo:" + userService.userInfo
                    + " userService.testFlag:" + userService.testFlag
                    + "; PropertyValues:" +pvs);

            MutablePropertyValues mpvs = new MutablePropertyValues(pvs);
            mpvs.add("testFlag", "The Flag is modified by PropertyValues.");
            return mpvs;
        }
        return pvs;
    }

    // 8. 在 Bean 初始化前执行的回调方法
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof UserService) {
            System.out.println(beanName+": BeanPostProcessor.postProcessBeforeInitialization");
        }
        return bean;
    }

    // 11. 在 Bean 初始化后执行的回调方法
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof UserService) {
            System.out.println(beanName + ": BeanPostProcessor.postProcessAfterInitialization");
        }
        return bean;
    }



}
