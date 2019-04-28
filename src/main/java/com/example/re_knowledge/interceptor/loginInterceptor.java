package com.example.re_knowledge.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        long startime = System.currentTimeMillis();
        request.setAttribute("startime",startime);
        String username = (String) request.getSession().getAttribute("username");
        logger.info("请求的类型为:" + request.getMethod());
        if(username == null){
            flag = false;
            response.sendRedirect("/login");
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startime = (long) request.getAttribute("startime");
        long endtime = System.currentTimeMillis();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String username = (String) request.getSession().getAttribute("username");
        System.out.println("用户名为:" + username + "\t的用户" + "\t请求:" + url + "\t\t完成!!");
    }

}
