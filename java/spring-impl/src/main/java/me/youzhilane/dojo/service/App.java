package me.youzhilane.dojo.service;

import me.youzhilane.dojo.spring.ApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.test();

    }
}
