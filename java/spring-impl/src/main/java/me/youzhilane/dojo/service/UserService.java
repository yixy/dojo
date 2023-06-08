package me.youzhilane.dojo.service;

import me.youzhilane.dojo.spring.*;
import me.youzhilane.dojo.spring.annotation.Autowired;
import me.youzhilane.dojo.spring.annotation.Component;
import me.youzhilane.dojo.spring.annotation.Scope;

@Component("userService")
@Scope("prototype")
public class UserService implements BeanNameAware, BeanInitialization {
    @Autowired
    private UserInfo userInfo;
    private String beanName;

    public void test(){
        System.out.println(this.userInfo);
    }

    //get beanName from spring
    @Override
    public void setBeanName(String beanName) {
        this.beanName=beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(this.beanName+" initializing...");
    }
}
