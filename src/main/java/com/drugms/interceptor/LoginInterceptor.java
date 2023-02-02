package com.drugms.interceptor;

import com.drugms.common.CustomException;
import com.drugms.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURL=request.getRequestURI();
//        log.info("preHandle Interceptor路径: "+requestURL);
        //获取到session，可以直接放行
        if(request.getSession().getAttribute("admin")!=null) return true;
        //检查cookie是否带有token
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
//                    log.info("get token: {}",token);
                    String username= (String) RedisUtils.getValue(token);
                    if(username!=null && !username.equals("")){
                        request.getSession().setAttribute("admin", username);
                        return true;
                    }
                }
            }
        }
        throw new CustomException("未授权的访问");
    }
}
