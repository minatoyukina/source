package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Slf4j
@Order(1)
@Component
public class SlowSqlInterceptor implements Interceptor {

    private Double noticeTime = 5.0;

    /**
     * Intercept object.
     *
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<String> paramList = getParamList(configuration, boundSql);
        Object proceed = invocation.proceed();
        int result = 0;
        if (proceed instanceof ArrayList) {
            ArrayList resultList = (ArrayList) proceed;
            result = resultList.size();
        }
        if (proceed instanceof Integer) {
            result = (Integer) proceed;
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        if (time >= noticeTime * 1000) {
            log.error("执行超过{}秒,sql={}", noticeTime, sql);
            log.error("result={}, time={}ms, params={}", result, time, paramList);
            return proceed;
        }
        log.info("sql={}", sql);
        log.info("result={},time={}ms, params={}", result, time, paramList);
        return proceed;
    }

    /**
     * Instantiates a new Sql log interceptor.
     */
    public SlowSqlInterceptor() {
        log.info("[打印SQL拦截器创建]noticeTime={}秒", noticeTime);
    }

    /**
     * 设置执行sql
     *
     * @param noticeTime 关注时间 ，如果执行sql超过关注时间，打印error日志.
     */
    public SlowSqlInterceptor(Double noticeTime) {
        this.noticeTime = noticeTime;
        log.info("[打印SQL拦截器创建]noticeTime={}秒", noticeTime);
    }

    /**
     * Plugin object.
     *
     * @param target the target
     * @return the object
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取sql参数集合。
     *
     * @param configuration the configuration
     * @param boundSql      the bound sql
     * @return the param list
     */
    private List<String> getParamList(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> params = new ArrayList<>();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                params.add(getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        params.add(getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        params.add(getParameterValue(obj));
                    }
                }
            }
        }
        return params;
    }

    private String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

}
