package com.example.re_knowledge.entry.no_table;

public class Message {
    private int code;
    private String msg;

    public Message() {
    }

    public Message success(){
        Message msg = new Message();
        msg.setCode(200);
        msg.setMsg("成功!!");
        return msg;
    }

    public Message fail(){
        Message msg = new Message();
        msg.setCode(404);
        msg.setMsg("失败!!");
        return msg;
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
}
