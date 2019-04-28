package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class comment implements RowMapper<comment>{
    private String comment_id;
    private String course_id;
    private String comment;
    private String time;
    private String realname;
    private String work_id;

    public comment() {
    }

    public comment(String comment_id, String course_id, String comment, String time, String realname, String work_id) {
        this.comment_id = comment_id;
        this.course_id = course_id;
        this.comment = comment;
        this.time = time;
        this.realname = realname;
        this.work_id = work_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    @Override
    public comment mapRow(ResultSet resultSet, int i) throws SQLException {
        comment comment1 = new comment();
        comment1.setComment(resultSet.getString("comment"));
        comment1.setComment_id(resultSet.getString("comment_id"));
        comment1.setCourse_id(resultSet.getString("course_id"));
        comment1.setRealname(resultSet.getString("realname"));
        comment1.setTime(resultSet.getString("time"));
        comment1.setWork_id(resultSet.getString("work_id"));
        return comment1;
    }
}
