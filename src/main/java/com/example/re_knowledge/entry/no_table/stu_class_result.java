package com.example.re_knowledge.entry.no_table;

import com.example.re_knowledge.entry.classes;
import com.example.re_knowledge.entry.course;
import com.example.re_knowledge.entry.stu_class;
import com.example.re_knowledge.entry.user;



//一个失误的jdbc查询结果类。用来封装用户表，以及对应班级和课程表的类。
public class stu_class_result {
    private user User1;
    private stu_class class1;
    private course course1;

    public stu_class_result() {
    }

    public stu_class_result(user user1, stu_class class1, course course1) {
        User1 = user1;
        this.class1 = class1;
        this.course1 = course1;
    }

    public user getUser1() {
        return User1;
    }

    public void setUser1(user user1) {
        User1 = user1;
    }

    public stu_class getClass1() {
        return class1;
    }

    public void setClass1(stu_class class1) {
        this.class1 = class1;
    }

    public course getCourse1() {
        return course1;
    }

    public void setCourse1(course course1) {
        this.course1 = course1;
    }
}
