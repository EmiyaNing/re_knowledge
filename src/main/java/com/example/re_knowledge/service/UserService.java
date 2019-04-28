package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.user;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public interface UserService{
    public boolean addUser(user User1);
    public boolean delByWork_id(String work_id);
    public boolean updUserDetial(user User1);
    public boolean updUserPassword(user User1);
    public boolean updUserStatus(user User1);
    public user findUserByUsername(String username);
    public user findUserByWork_id(String work_id);
    public List<user> findUserByStatus(int status);
    public List<user> findAll();
}
