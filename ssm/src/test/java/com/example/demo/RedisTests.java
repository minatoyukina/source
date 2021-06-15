package com.example.demo;

import com.example.demo.entity.Blog;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.RMapper;
import org.redisson.api.mapreduce.RReducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ccq
 * @since 2021/5/27 17:14
 */
@SpringBootTest
class RedisTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void ping() {
        redissonClient.<Blog>getBucket("abc").set(new Blog().setTitle("bcd"));
        System.out.println(redissonClient.getBucket("abc").getCodec());
    }

    @Test
    void test() {
        Map<String, Integer> execute = redissonClient.<String, String>getMap("mr").<String, Integer>mapReduce()
                .mapper((RMapper<String, String, String, Integer>) (key, value, collector) -> {
                    String[] split = value.split(" ");
                    for (String s : split) {
                        collector.emit(s, 1);
                    }
                })
                .reducer((RReducer<String, Integer>) (reducedKey, iter) -> {
                    int sum = 0;
                    while (iter.hasNext()) {
                        Integer i = iter.next();
                        sum += i;
                    }
                    return sum;
                })
                .execute();
        redissonClient.shutdown(0, 60, TimeUnit.SECONDS);
        System.out.println(execute);
    }
}
