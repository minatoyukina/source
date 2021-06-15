package com.example.cloud.gateway;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ccq
 * @since 2021/5/18 18:11
 */
class SimpleTests {

    @SneakyThrows
    @Test
    void test() {
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                RestTemplate restTemplate = new RestTemplate();
                String s = restTemplate.getForObject("http://localhost:8000/p/demo", String.class);
                System.out.println(s);
                latch.countDown();
            });
        }
        latch.await();
        service.shutdown();
    }
}
