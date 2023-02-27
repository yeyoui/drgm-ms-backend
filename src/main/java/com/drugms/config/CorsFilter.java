package com.drugms.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 过滤器解决跨域问题
 */
@Component
public class CorsFilter implements Filter {

    private List<String> ALLOW_ORIGINS= Arrays.asList(
            "http://localhost:8065","http://localhost:7777"
    );

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8065");
        setCrosHeader(request.getHeader("Origin"),response);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(req, res);

    }
    //设置Access-Control-Allow-Origin
    private void setCrosHeader(String reqOrigin,HttpServletResponse response){
        if(reqOrigin==null) return;
        //匹配的地址才设置
        if (ALLOW_ORIGINS.contains(reqOrigin)) response.setHeader("Access-Control-Allow-Origin",reqOrigin);
        response.setHeader("Access-Control-Allow-Origin",reqOrigin);
    }


}
