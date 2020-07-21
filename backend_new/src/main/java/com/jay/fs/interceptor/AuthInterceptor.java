package com.jay.fs.interceptor;

import com.jay.fs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录状态拦截器
 * 负责拦截未登录的请求
 * @author Jay
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/json;charset=utf-8");

        // 从header获取token
        String token = request.getHeader("token");

        // token为空，表示处于未登录状态
        if(StringUtils.isEmpty(token)){
            response.getWriter().write("{\"message\":\"你还没有登录\"}");
            return false;
        }
        else {
            // token验证失败，token不合法
            if(TokenUtil.verify(token)==false){
                response.getWriter().write("{\"message\":\"token错误，无法访问\"}");
                return false;
            }
            else{
                return true;
            }
        }
    }
}
