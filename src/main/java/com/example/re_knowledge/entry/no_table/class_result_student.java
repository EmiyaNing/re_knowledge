package com.example.re_knowledge.entry.no_table;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class class_result_student implements RowMapper<class_result_student> {
    private String classnum;
    private String name;
    private String work_id;
    private String realname;
    private String course_id;
    private int score;
    private int avgscore;

    public class_result_student() {
    }

    public class_result_student(String classnum, String name, String work_id, String realname, int score, int avgscore,String course_id) {
        this.classnum = classnum;
        this.name = name;
        this.work_id = work_id;
        this.realname = realname;
        this.score = score;
        this.avgscore = avgscore;
        this.course_id = course_id;
    }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(int avgscore) {
        this.avgscore = avgscore;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    @Override
    public class_result_student mapRow(ResultSet resultSet, int i) throws SQLException {
        class_result_student result = new class_result_student();
        result.setAvgscore(resultSet.getInt("avgscore"));
        result.setClassnum(resultSet.getString("classnum"));
        result.setName(resultSet.getString("name"));
        result.setRealname(resultSet.getString("realname"));
        result.setScore(resultSet.getInt("score"));
        result.setWork_id(resultSet.getString("work_id"));
        result.setCourse_id(resultSet.getString("course_id"));
        return null;
    }
}
