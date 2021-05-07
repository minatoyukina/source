package com.example.demo.aop;

import java.lang.annotation.*;

/**
 * @author ccq
 * @since 2021/5/7 14:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";
}
