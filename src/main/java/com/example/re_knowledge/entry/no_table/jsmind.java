package com.example.re_knowledge.entry.no_table;

import com.example.re_knowledge.entry.course_node;

import java.util.List;

public class jsmind {
    private Meta meta;
    private String format;
    private List<course_node> data;

    public void success(List<course_node> result,String author,String name){
        this.data = result;
        this.meta = new Meta(name,author,"1");
        this.format = "node_array";
        System.out.println("初始化完成");
    }

    public jsmind(Meta meta, String format, List<course_node> data) {
        this.meta = meta;
        this.format = format;
        this.data = data;
    }

    public jsmind() {
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<course_node> getData() {
        return data;
    }

    public void setData(List<course_node> data) {
        this.data = data;
    }
}
