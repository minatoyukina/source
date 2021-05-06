package com.example.demo;

import com.example.demo.config.SlowSqlInterceptor;
import com.example.demo.entity.Blog;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;

class SimpleTests {

    @Test
    void test() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/re_blog?serverTimezone=GMT%2B8&useSSL=false");
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
    @SneakyThrows
    void test1() {

    }
}
