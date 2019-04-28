package com.example.re_knowledge.entry.no_table;

import java.util.List;

public class file_data {
    private int code;
    private String msg;
    private int count;
    private List<file_name> data;

    public file_data() {
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

    public List<file_name> getData() {
        return data;
    }

    public void setData(List<file_name> data) {
        this.data = data;
    }

    public void success(List<file_name> data){
        this.setCode(0);
        this.setMsg("success");
        this.setData(data);
        this.setCount(data.size());
    }
}
