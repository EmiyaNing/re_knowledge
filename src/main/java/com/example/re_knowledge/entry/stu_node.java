package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class stu_node implements RowMapper<stu_node> {
    private String ID;
    private String course_id;
    private String work_id;
    private int score;
    private int weight;
    private String title;

    public stu_node() {
    }

    public stu_node(String ID, String course_id, String work_id, int score, int weight, String title) {
        this.ID = ID;
        this.course_id = course_id;
        this.work_id = work_id;
        this.score = score;
        this.weight = weight;
        this.title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public stu_node mapRow(ResultSet resultSet, int i) throws SQLException {
        stu_node snode = new stu_node();
        snode.setCourse_id(resultSet.getString("course_id"));
        snode.setID(resultSet.getString("ID"));
        snode.setScore(resultSet.getInt("score"));
        snode.setTitle(resultSet.getString("title"));
        snode.setWork_id(resultSet.getString("work_id"));
        snode.setWeight(resultSet.getInt("weight"));
        return snode;
    }
}
