package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class user implements RowMapper<user>{
    private String username;
    private String password;
    private String realname;
    private String work_id;
    private String phone;
    private String emial;
    private int status;                             //0为学生，1为老师。

    public user() {
    }

    public user(String username, String password, String realname, String work_id, String phone, String emial, int status) {

        this.username = username;
        this.password = password;
        this.realname = realname;
        this.work_id = work_id;
        this.phone = phone;
        this.emial = emial;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public user mapRow(ResultSet resultSet, int i) throws SQLException {
        user User1 = new user();
        User1.setUsername(resultSet.getString("username"));
        User1.setPassword(resultSet.getString("password"));
        User1.setRealname(resultSet.getString("realname"));
        User1.setWork_id(resultSet.getString("work_id"));
        User1.setEmial(resultSet.getString("email"));
        User1.setPhone(resultSet.getString("phone"));
        User1.setStatus(resultSet.getInt("status"));
        return User1;
    }
}
