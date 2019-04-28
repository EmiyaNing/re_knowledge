package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class course implements RowMapper<course>{
    private String course_id;
    private String name;
    private String work_id;
    private String description;

    public course() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public course(String course_id, String name, String work_id, String description) {

        this.course_id = course_id;
        this.name = name;
        this.work_id = work_id;
        this.description = description;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    @Override
    public course mapRow(ResultSet resultSet, int i) throws SQLException {
        course course1 = new course();
        course1.setCourse_id(resultSet.getString("course_id"));
        course1.setWork_id(resultSet.getString("work_id"));
        course1.setName(resultSet.getString("name"));
        course1.setDescription(resultSet.getString("description"));
        return course1;
    }
}
