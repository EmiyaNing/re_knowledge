package com.example.re_knowledge.entry.no_table;

import com.example.re_knowledge.entry.user;

import java.util.List;

public class UserMessage {
    private int code;
    private String msg;
    private int count;
    private List<user> data;

    public void success(List<user> users){
        this.data = users;
        this.code = 0;
        this.count = users.size();
    }

    public UserMessage() {
    }

    public UserMessage(int code, String msg, int count, List<user> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<user> getData() {
        return data;
    }

    public void setData(List<user> data) {
        this.data = data;
    }
}
