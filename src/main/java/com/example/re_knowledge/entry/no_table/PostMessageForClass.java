package com.example.re_knowledge.entry.no_table;

import java.util.List;


//返回班级界面所用的数据表格的json数据。。
public class PostMessageForClass {
    private int code;
    private String msg;
    private int count;
    private List<class_result_student> data;

    public void success(List<class_result_student> result){
        this.setMsg("success!!");
        this.setCode(0);
        this.setCount(result.size());
        this.setData(result);
    }

    public PostMessageForClass() {
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

    public List<class_result_student> getData() {
        return data;
    }

    public void setData(List<class_result_student> data) {
        this.data = data;
    }
}
