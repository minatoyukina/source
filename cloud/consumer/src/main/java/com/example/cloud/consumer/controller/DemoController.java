package com.example.cloud.consumer.controller;

import com.example.cloud.consumer.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ccq
 * @since 2021/5/18 14:55
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping
    public String hello() {
        return demoService.hello();
    }
}
