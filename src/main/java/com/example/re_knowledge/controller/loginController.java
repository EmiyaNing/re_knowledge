package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.no_table.Message;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 该文件主要用来处理登陆的请求。
 * 处理的url：
 *         yuming/login           Get请求        返回登陆界面
 *         yuming/login           Post请求       接受前端的登陆请求，并对传入的数据进行比对。
 */
@RestController
public class loginController {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回登陆的界面。
     * @return
     */
    @GetMapping("/login")
    public ModelAndView loginpage(){
        ModelAndView model = new ModelAndView("login");
        return model;
    }

    /**
     * 处理登陆的请求。
     * @param request  前端发起的url为 域名/login 的请求体
     * @param response 后端给该请求的回应
     * @param session  由该用户的cookie生成的session
     * @return
     * @throws IOException
     * @throws EmptyResultDataAccessException
     */
    @PostMapping("/login")
    public Message login(HttpServletRequest request, HttpServletResponse response, HttpSession session)throws IOException,EmptyResultDataAccessException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Message msg = new Message();
        logger.info("username = " + username + "\tpassword = " + password);
        user User1 = userService.findUserByUsername(username);
        session.setAttribute("username",username);
        session.setAttribute("work_id",User1.getWork_id());
        session.setAttribute("status",User1.getStatus());
        if(User1 == null){
            response.sendRedirect("/login");
            return msg.fail();
        }else if(User1.getPassword().equals(password)){
            if(User1.getStatus() == 0){
                session = request.getSession();                                 //登陆时创建session.
                response.sendRedirect("/studenthome");
            }else if(User1.getStatus() == 1){
                session = request.getSession();                                 //登陆时创建session.
                response.sendRedirect("/teacherhome");
            }else{
                response.sendRedirect("/login");
                return msg.fail();
            }
            return msg.success();
        }else{
            response.sendRedirect("/login");
            return msg.fail();
        }
    }

    
}
