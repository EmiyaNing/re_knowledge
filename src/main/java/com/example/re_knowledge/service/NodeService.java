package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.course_node;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.util.List;

public interface NodeService {
    public List<course_node> findByCourse_id(String course_id);
    public boolean insertnode(course_node node) throws MySQLIntegrityConstraintViolationException;
    public boolean deletenodeBycourse_id(String course_id);
    public course_node findNodeByCourse_idAndnodeid(String course_id,String nodeid);
    public boolean updateNodeDes(float weight,String description,String course_id,String nodeid);
}
