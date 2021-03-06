package com.example.demo.service;

import com.example.demo.entity.Blog;

import java.util.List;

/**
 * @author ccq
 * @since 2021/5/7 14:50
 */
public interface BlogService {

    List<Blog> listBlog();

    void saveBlog(Blog blog);

    void testSave(Blog blog);

    Blog testGet(Integer id);

    void testUpdate(Blog blog);
}
