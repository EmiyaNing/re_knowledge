package com.example.re_knowledge.entry;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class course_node implements RowMapper<course_node>{
    private String course_id;
    private String ID;
    private String parentid;
    private boolean isroot;
    private float weight;
    private String topic;
    private String description;
    private String answer;
    private String question;
    private int avgscore;

    public course_node() {
    }

    public course_node(String course_id, String ID, String parentID, boolean isroot,float weight, String topic, String description, String answer, String question, int avgscore) {
        this.course_id = course_id;
        this.ID = ID;
        this.parentid = parentID;
        this.isroot = isroot;
        this.weight = weight;
        this.topic = topic;
        this.description = description;
        this.answer = answer;
        this.question = question;
        this.avgscore = avgscore;
    }

    public boolean getIsroot() {
        return isroot;
    }

    public void setIsroot(boolean isroot) {
        this.isroot = isroot;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentID(String parentid) {
        this.parentid = parentid;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(int avgscore) {
        this.avgscore = avgscore;
    }

    @Override
    public course_node mapRow(ResultSet resultSet, int i) throws SQLException {
        course_node node = new course_node();
        node.setAnswer(resultSet.getString("answer"));
        node.setAvgscore(resultSet.getInt("avgscore"));
        node.setCourse_id(resultSet.getString("course_id"));
        node.setDescription(resultSet.getString("description"));
        node.setIsroot(resultSet.getBoolean("isroot"));
        node.setID(resultSet.getString("ID"));
        node.setParentID(resultSet.getString("parentID"));
        node.setQuestion(resultSet.getString("question"));
        node.setTopic(resultSet.getString("topic"));
        node.setWeight(resultSet.getInt("weight"));
        return node;
    }
}
