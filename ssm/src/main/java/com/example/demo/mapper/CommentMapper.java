package com.example.demo.mapper;

import com.example.demo.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {

    List<Comment> getByBlogId(Integer blogId);
}
