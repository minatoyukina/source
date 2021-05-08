package com.example.demo.service.impl;

import com.example.demo.entity.Blog;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.service.BlogService;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ccq
 * @since 2021/5/7 14:51
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private BlogMapper blogMapper;

    @Override
    public List<Blog> listBlog() {
        return jdbcTemplate.query("select * from blog", new BeanPropertyRowMapper<>(Blog.class));
    }

    @SneakyThrows
    @Override
    public void saveBlog(Blog blog) {
        TimeUnit.SECONDS.sleep(10);
        jdbcTemplate.update("insert into blog(title) values (" + blog.getTitle() + ")");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @SneakyThrows
    public void testSave(Blog blog) {
        TimeUnit.SECONDS.sleep(10);
        jdbcTemplate.update("insert into blog(title) values (" + blog.getTitle() + ")");
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Blog testGet(Integer id) {
        Thread.sleep(10_000);
        return blogMapper.getBlogById(id);
    }

}
