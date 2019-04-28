package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class classes implements RowMapper<classes>{
    private String course_id;
    private String work_id;
    private String classnum;
    private int avgscore;

    public classes() {
    }

    public classes(String course_id, String work_id, String classnum, int avgscore) {
        this.course_id = course_id;
        this.work_id = work_id;
        this.classnum = classnum;
        this.avgscore = avgscore;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
    }

    public int getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(int avgscore) {
        this.avgscore = avgscore;
    }

    @Override
    public classes mapRow(ResultSet resultSet, int i) throws SQLException {
        classes class1 = new classes();
        class1.setWork_id(resultSet.getString("work_id"));
        class1.setCourse_id(resultSet.getString("course_id"));
        class1.setClassnum(resultSet.getString("classnum"));
        class1.setAvgscore(resultSet.getInt("avgscore"));
        return class1;
    }
}
