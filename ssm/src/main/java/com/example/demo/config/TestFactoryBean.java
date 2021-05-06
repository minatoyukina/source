package com.example.demo.config;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ccq
 * @since 2021/5/6 14:20
 */
public class TestFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mapperInterface;

    public TestFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                }
                if (method.getName().contains("test")) {
                    return "hello";
                }
                return null;
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
