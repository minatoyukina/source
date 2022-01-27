package com.example.demo.mapper;

import com.example.demo.entity.Isolation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author chenchuanqi
 * @version 1.0
 * @since 2021/10/13 14:31
 */
@Repository
public interface IsolationMapper {

    Isolation getById(Long id);

    Integer update(@Param("id") Long id, @Param("amount") Double amount);
}
