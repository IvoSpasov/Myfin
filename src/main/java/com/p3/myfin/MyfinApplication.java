package com.p3.myfin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MyfinApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(MyfinApplication.class, args);
//        for (String beanName : ac.getBeanDefinitionNames()) {
//            System.out.println(beanName);
//        }
    }

}
