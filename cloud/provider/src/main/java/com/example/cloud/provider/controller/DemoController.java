package com.example.cloud.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccq
 * @since 2021/5/18 14:52
 */
@RestController
@RequestMapping("demo")
public class DemoController {

    @GetMapping
    public String hello() {
        return "hello";
    }
}
