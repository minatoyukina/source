package com.example.demo;

import com.example.demo.config.CommentFactoryBean;
import com.example.demo.config.MyAware;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.BlogService;
import com.example.demo.test.ITest1;
import com.example.demo.test.ITest2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SsmApplicationTests {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ITest2 test2;

    @Autowired
    private ITest1 test1;

    @Autowired
    private Blog blog;

    @Autowired
    private CommentFactoryBean commentFactoryBean;

    @Autowired
    private Comment comment;

    @Autowired
    private BlogService blogService;

    @Test
    void contextLoads() {
        System.out.println(blogMapper);
        System.out.println(commentMapper);
    }

    @Test
    void lazy() {
        Blog blog = blogMapper.getBlogById(15);
        System.out.println(blog.getId());
        Blog lazy = blogMapper.getBlogLazy(15);
        System.out.println(lazy.getCommentList());
    }

    @Test
    void test() {
        System.out.println(test1);
        System.out.println(test2);
        System.out.println(test1.add(1, 2));
        System.out.println(test2.hello());
    }

    @Test
    void blog() {
        System.out.println(blog);
    }

    @Test
    void comment() {
        Comment comment1 = commentFactoryBean.getObject();
        Comment comment2 = commentFactoryBean.getObject();
        System.out.println(comment);
        System.out.println(comment1);
        System.out.println(comment2);
    }

    @Test
    void context() {
        ApplicationContext context = MyAware.applicationContext;
        Blog bean = context.getBean(Blog.class);
        System.out.println(bean);
    }

    @Test
    void transaction() {
        Blog blog = new Blog();
        blogService.testSave(blog);
    }


}
