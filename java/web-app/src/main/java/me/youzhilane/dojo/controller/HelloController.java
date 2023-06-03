package me.youzhilane.dojo.controller;

import jakarta.annotation.Resource;
import me.youzhilane.dojo.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Resource
    private HelloService helloService;

    @GetMapping("say")
    public String say(String username){
        //http://localhost:8080/say?username=tom
        return helloService.say(username);
    }
}
