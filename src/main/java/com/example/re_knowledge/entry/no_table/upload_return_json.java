package com.example.re_knowledge.entry.no_table;

public class upload_return_json {
    private int code;
    private String msg;

    public upload_return_json() {
    }

    public void success(){
        this.setCode(0);
        this.setMsg("success");
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
