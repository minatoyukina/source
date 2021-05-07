package com.example.demo.test;

import com.example.demo.aop.Log;

/**
 * @author ccq
 * @since 2021/5/6 14:29
 */
public interface ITest1 {

    @Log
    int add(int x, int y);
}
