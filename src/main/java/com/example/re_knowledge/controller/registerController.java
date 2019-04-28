package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.no_table.Message;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 该文件主要处理注册请求
 * 处理url：
 *          yuming/signup            Get请求              返回注册界面
 *          yuming/signup            Post请求             接受前端的注册信息
 *
 */
@RestController
public class registerController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public ModelAndView signpage(){
        ModelAndView model = new ModelAndView("register");
        return model;
    }

    @PostMapping("/signup")
    public Message signup(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String work_id = request.getParameter("work_id");
        user User1 = new user();
        User1.setStatus(0);
        User1.setUsername(username);
        User1.setWork_id(work_id);
        User1.setPassword(password);
        if(userService.addUser(User1)){
            response.sendRedirect("/studenthome");
            session = request.getSession();                                 //注册成功，进入主页，创建session
            return new Message().success();
        }else{
            response.sendRedirect("/login");
            return new Message().fail();
        }
    }

}
