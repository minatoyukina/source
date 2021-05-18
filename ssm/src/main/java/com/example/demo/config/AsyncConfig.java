package com.example.demo.config;

import com.example.demo.mapper.BlogMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author ccq
 * @since 2021/5/13 17:33
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Resource
    private BlogMapper blogMapper;

    @Async
    public Future<Void> test() {
        blogMapper.updateBlog(1, "bbb");
        return new AsyncResult<>(null);
    }
}
