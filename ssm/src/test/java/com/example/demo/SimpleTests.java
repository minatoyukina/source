package com.example.demo;

import com.example.demo.config.SlowSqlInterceptor;
import com.example.demo.entity.Blog;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

class SimpleTests {

    @Test
    void test() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriver("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/re_blog?serverTimezone=GMT%2B8&useSSL=false");
        Environment environment = new Environment("dev", new JdbcTransactionFactory(), dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addInterceptor(new SlowSqlInterceptor(0.01));
        configuration.addMapper(BlogMapper.class);
        configuration.addMapper(CommentMapper.class);
        SqlSessionFactory factory = new DefaultSqlSessionFactory(configuration);
        SqlSession sqlSession = factory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.getBlogLazy(15);
        System.out.println(blog.getCommentList());
    }

    @Test
    void future() {
        ListenableFutureTask<String> future = new ListenableFutureTask<>(() -> {
            System.out.println("world");
            return "hello";
        });
        future.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(@NonNull Throwable ex) {

            }

            @Override
            @SneakyThrows
            public void onSuccess(String result) {
                Thread.sleep(1000);
                System.out.println(result);
            }
        });
        future.run();
    }
}
