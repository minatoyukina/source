package com.example.demo.service;

/**
 * @author chenchuanqi
 * @version 1.0
 * @since 2021/10/13 14:37
 */
public interface IsolationService {

    void update1(Long id, Double amount);

    void update2(Long id, Double amount);

    void nested();
}
