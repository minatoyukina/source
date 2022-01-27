package com.example.demo.controller;

import com.example.demo.service.IsolationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * lost update
 *
 * @author chenchuanqi
 * @version 1.0
 * @since 2021/10/13 14:28
 */
@RestController
@RequestMapping("isolation")
public class IsolationController {

    @Resource
    private IsolationService isolationService;

    @GetMapping("1")
    public String test1() {
        isolationService.update1(1L, 30d);
        return "1";
    }

    @GetMapping("2")
    public String test2() {
        isolationService.update2(1L, 10d);
        return "2";
    }

    @GetMapping("3")
    public String test3() {
        isolationService.nested();
        return "3";
    }
}
