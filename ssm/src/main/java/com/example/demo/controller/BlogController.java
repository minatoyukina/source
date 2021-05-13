package com.example.demo.controller;

import com.example.demo.entity.Blog;
import com.example.demo.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ccq
 * @since 2021/5/7 17:10
 */
@RestController
@RequestMapping("blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @GetMapping
    public List<Blog> list() {
        return blogService.listBlog();
    }

    @GetMapping("{id}")
    public Blog get(@PathVariable Integer id) {
        return blogService.testGet(id);
    }

    @PostMapping
    public Blog save(String title) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blogService.testSave(blog);
        return blog;
    }

    @PutMapping("{id}")
    public Blog update(String title, @PathVariable Integer id) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setId(id);
        blogService.testUpdate(blog);
        return blog;
    }
}
