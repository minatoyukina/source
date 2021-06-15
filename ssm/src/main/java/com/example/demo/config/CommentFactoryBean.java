package com.example.demo.config;

import com.example.demo.aop.Log;
import com.example.demo.entity.Comment;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author ccq
 * @since 2021/5/7 11:45
 */
@Component
public class CommentFactoryBean implements FactoryBean<Comment> {

    @Override
    @Log("test")
    public Comment getObject() {
        return new Comment();
    }

    @Override
    public Class<?> getObjectType() {
        return Comment.class;
    }
}
