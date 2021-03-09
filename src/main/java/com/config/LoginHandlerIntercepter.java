package com.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user =request.getSession().getAttribute("user");
        //未登录
        if(user==null){
            request.setAttribute("msg","请先登录！");
            request.getRequestDispatcher("/role.html").forward(request,response);
            return false;
        }else {
            return true;
        }
    }
}
