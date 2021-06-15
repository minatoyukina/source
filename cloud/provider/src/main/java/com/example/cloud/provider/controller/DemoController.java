package com.example.cloud.provider.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author ccq
 * @since 2021/5/18 14:52
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @SneakyThrows
    @GetMapping
    public String hello() {
        int i = new Random().nextInt(10);
        Thread.sleep(i * 100);
        return "hello";
    }
}
