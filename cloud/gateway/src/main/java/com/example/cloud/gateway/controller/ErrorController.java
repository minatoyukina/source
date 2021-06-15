package com.example.cloud.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author ccq
 * @since 2021/5/18 17:17
 */
@RestController
public class ErrorController {

    @GetMapping("501")
    public Mono<String> _501() {
        return Mono.just("501");
    }
}
