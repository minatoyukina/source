package com.example.demo.service.impl;

import com.example.demo.entity.Blog;
import com.example.demo.service.BlogService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ccq
 * @since 2021/5/7 14:51
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Blog> listBlog() {
        return jdbcTemplate.query("select * from blog", new BeanPropertyRowMapper<>(Blog.class));
    }
}
