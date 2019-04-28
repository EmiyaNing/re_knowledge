package com.example.re_knowledge.service.imp;


import com.example.re_knowledge.entry.classes;
import com.example.re_knowledge.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceimp implements ClassService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassServiceimp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<classes> findBYwork_id(String work_id) {
        String sql = "select * from classes where work_id = ?";
        List<classes> results = jdbcTemplate.query(sql,new Object[]{work_id},new BeanPropertyRowMapper<classes>(classes.class));
        return results;
    }

    @Override
    public boolean addClass(classes class1) {
        String sql = "insert into classes (course_id,work_id,classnum,avgscore) value (?,?,?,?)";
        boolean res = jdbcTemplate.update(sql,class1.getCourse_id(),class1.getWork_id(),class1.getClassnum(),class1.getAvgscore()) > 0;
        return res;
    }

    @Override
    public boolean deleteClassByClassnumAndCourseid(String classnum,String course_id) {
        String sql  = "delete from classes where classnum = ? and course_id = ?";
        String sql1 = "delete from stu_class where classnum = ? and course_id = ?";
        String sql2 = "delete from stu_node where course_id = ?";
        boolean res  = jdbcTemplate.update(sql,classnum,course_id) > 0;
        boolean res1 = jdbcTemplate.update(sql1,classnum,course_id) > 0;
        boolean res2 = jdbcTemplate.update(sql2,course_id) > 0;
        return res & res1 & res2;
    }
}
