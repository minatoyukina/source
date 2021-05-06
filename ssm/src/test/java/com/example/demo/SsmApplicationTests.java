package com.example.demo;

import com.example.demo.dao.ITest;
import com.example.demo.mapper.BlogMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SsmApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private ITest test;

    @Test
    @SneakyThrows
    void contextLoads() {
        System.out.println(blogMapper);
    }

    @Test
    void test() {
        System.out.println(test);
        System.out.println(test.test());
    }


}
