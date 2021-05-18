package com.example.cloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ccq
 * @since 2021/5/18 14:54
 */
@FeignClient("cloud-provider")
@Service
public interface DemoService {

    @GetMapping("demo")
    String hello();
}
