package com.example.re_knowledge.service.imp;


import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimp implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceimp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addUser(user User1){
        String sql = "insert into user(username,password,realname,work_id,phone,email,status) values(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,User1.getUsername(),User1.getPassword(),User1.getRealname(),User1.getWork_id(),User1.getPhone(),User1.getEmial(),User1.getStatus()) > 0;

    }

    @Override
    public boolean delByWork_id(String work_id){
        String sql = "delete from user where work_id = ?";
        String sql1 = "delete from stu_class where work_id = ?";
        String sql2 = "delete from stu_node where work_id = ?";
        boolean res1 = jdbcTemplate.update(sql,work_id) > 0;
        boolean res2 = jdbcTemplate.update(sql1,work_id) > 0;
        boolean res3 = jdbcTemplate.update(sql2,work_id) > 0;
        return (res1 & res2 & res3);

    }

    @Override
    public boolean updUserDetial(user User1){
        String sql = "update user set realname = ?,phone = ?,email = ? where work_id = ?";
        return jdbcTemplate.update(sql,User1.getRealname(),User1.getPhone(),User1.getEmial(),User1.getWork_id()) > 0;
    }

    @Override
    public boolean updUserPassword(user User1){
        String sql = "update user set password = ? where work_id = ?";
        return jdbcTemplate.update(sql,User1.getPassword(),User1.getWork_id()) > 0;
    }

    @Override
    public boolean updUserStatus(user User1){
        String sql = "update user set status = ? where work_id = ?";
        String sql1 = "delete from stu_class where work_id = ?";
        String sql2 = "delete form stu_node where work_id";
        boolean res1 = jdbcTemplate.update(sql,User1.getStatus(),User1.getWork_id()) > 0;
        boolean res2 = jdbcTemplate.update(sql1,User1.getWork_id()) > 0;
        boolean res3 = jdbcTemplate.update(sql2,User1.getWork_id()) > 0;
        return (res1 & res2 & res3);

    }

    @Override
    public user findUserByUsername(String username){
        String sql = "select * from user where username = ?";
        user User1 = jdbcTemplate.queryForObject(sql,new user(),username);
        System.out.println(User1.getWork_id());
        return User1;

    }

    @Override
    public user findUserByWork_id(String work_id){
        String sql = "select * from user where work_id = ?";
        user User1 = jdbcTemplate.queryForObject(sql,new user(),new Object[]{work_id});
        if(User1 != null){
            return User1;
        }else{
            return null;
        }

    }

    @Override
    public List<user> findUserByStatus(int status){
        String sql = "select * from user where status = ?";
        List<user> userlist = jdbcTemplate.query(sql,new Object[]{status},new BeanPropertyRowMapper<user>(user.class));
        if(userlist != null && userlist.size() > 0){
            return userlist;
        }else{
            return userlist;
        }

    }

    /**
     * 这个函数的功能是。
     * @return
     */
    @Override
    public List<user> findAll() {
        String sql = "select * from user";
        List<user> result = jdbcTemplate.query(sql,new BeanPropertyRowMapper<user>(user.class));
        return result;
    }

}
