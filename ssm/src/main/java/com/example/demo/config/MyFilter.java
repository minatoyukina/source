package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author ccq
 * @since 2021/5/14 16:24
 */
@Configuration
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(filterChain);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
