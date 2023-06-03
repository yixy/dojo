package me.youzhilane.dojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
//        System.out.println(Arrays.stream(args).toList());
        SpringApplication.run(Application.class,args);
    }
}
