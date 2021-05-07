package com.example.demo.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;

/**
 * @author ccq
 * @since 2021/5/7 15:14
 */
@Configuration
public class MyListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        System.out.println("===========app start============");
        ApplicationContext applicationContext = event.getApplicationContext();
        System.out.println(applicationContext.getId());
    }
}
