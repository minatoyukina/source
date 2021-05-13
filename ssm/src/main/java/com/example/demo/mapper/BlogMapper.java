package com.example.demo.mapper;

import com.example.demo.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper {

    Blog getBlogById(Integer id);

    Blog getBlogLazy(Integer id);

    void updateBlog(@Param("id") Integer id, @Param("title") String title);
}
