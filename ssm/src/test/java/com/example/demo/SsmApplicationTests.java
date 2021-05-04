package com.example.demo;

import com.example.demo.entity.Blog;
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
    private Blog blog;

    @Test
    @SneakyThrows
    void contextLoads() {
        Blog blog = blogMapper.getBlogLazy(15);
        System.out.println(blog.getTitle());
    }

    @Test
    void test() {
        System.out.println(blog);
    }


}
