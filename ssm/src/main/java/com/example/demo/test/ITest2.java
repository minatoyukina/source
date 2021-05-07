package com.example.demo.test;

import com.example.demo.aop.Log;

/**
 * @author ccq
 * @since 2021/5/6 14:29
 */
public interface ITest2 {

    @Log
    String hello();
}
