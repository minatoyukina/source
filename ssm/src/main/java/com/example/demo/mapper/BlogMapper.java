package com.example.demo.mapper;

import com.example.demo.entity.Blog;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper {

    Blog getBlogById(Integer id);

    Blog getBlogLazy(Integer id);
}
