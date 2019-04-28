package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class stu_class implements RowMapper<stu_class> {
    private String classnum;
    private String course_id;
    private String work_id;
    private int score;

    public stu_class() {
    }

    public stu_class(String classnum, String course_id, String work_id, int score) {
        this.classnum = classnum;
        this.course_id = course_id;
        this.work_id = work_id;
        this.score = score;
    }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public stu_class mapRow(ResultSet resultSet, int i) throws SQLException {
        stu_class sclass1 = new stu_class();
        sclass1.setCourse_id(resultSet.getString("course_id"));
        sclass1.setClassnum(resultSet.getString("classnum"));
        sclass1.setWork_id(resultSet.getString("work_id"));
        sclass1.setScore(resultSet.getInt("score"));
        return sclass1;
    }
}
