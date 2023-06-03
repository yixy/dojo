package me.youzhilane.dojo.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String say(String username){
        return "Hello,"+username;
    }
}
