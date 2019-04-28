package com.example.re_knowledge.service.imp;

import com.example.re_knowledge.entry.course;
import com.example.re_knowledge.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceimp implements CourseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseServiceimp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<course> findCourseByWork_id(String work_id) {
        String sql = "select * from course where work_id = ?";
        List<course> courses = jdbcTemplate.query(sql,new Object[]{work_id},new BeanPropertyRowMapper<course>(course.class));
        return courses;
    }

    @Override
    public course findCourseByCourse_id(String course_id) {
        String sql = "select * from course where course_id = ?";
        course course1 = jdbcTemplate.queryForObject(sql,new course(),new Object[]{course_id});
        return course1;
    }

    @Override
    public boolean updateCoursename(String course_id, String name) {
        String sql = "update course set name = ? where course_id = ?";
        boolean res1 = jdbcTemplate.update(sql,name,course_id) > 0;
        return res1;
    }

    @Override
    public boolean addCoursename(String course_id, String name, String work_id, String description) {
        String sql   = "insert into course (course_id,name,work_id,description) value (?,?,?,?)";
        boolean res1 = jdbcTemplate.update(sql,course_id,name,work_id,description) > 0;
        return res1;
    }

    @Override
    public boolean deleteByCourse_id(String course_id) {
        String sql   = "delete from course where course_id = ?";
        String sql1  = "delete from classes where course_id = ?";
        String sql2  = "delete from course_node where course_id = ?";
        String sql3  = "delete from stu_class where course_id = ?";
        String sql4  = "delete from stu_node where course_id = ?";
        boolean res1 = jdbcTemplate.update(sql,course_id) > 0;
        boolean res2 = jdbcTemplate.update(sql1,course_id) > 0;
        boolean res3 = jdbcTemplate.update(sql2,course_id) > 0;
        boolean res4 = jdbcTemplate.update(sql3,course_id) > 0;
        boolean res5 = jdbcTemplate.update(sql4,course_id) > 0;
        return res1 & res2 & res3 & res4 & res5;
    }
}
