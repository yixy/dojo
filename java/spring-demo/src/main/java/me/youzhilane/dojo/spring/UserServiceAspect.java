package me.youzhilane.dojo.spring;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAspect {

    @Pointcut("execution(* me.youzhilane.dojo.spring.UserService.test(..))")
    public void pointCutTest(){}

    @Before("pointCutTest()")
    public void beforeTest() {
        System.out.println("调用test方法前");
    }

    @After("pointCutTest()")
    public void afterTest() {
        System.out.println("调用test方法后");
    }
}