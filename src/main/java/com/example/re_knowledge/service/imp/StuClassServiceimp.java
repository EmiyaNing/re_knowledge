package com.example.re_knowledge.service.imp;

import com.example.re_knowledge.entry.classes;
import com.example.re_knowledge.entry.course;
import com.example.re_knowledge.entry.no_table.class_result_student;
import com.example.re_knowledge.entry.no_table.stu_class_result;
import com.example.re_knowledge.entry.stu_class;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.StuClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StuClassServiceimp implements StuClassService{
    private final JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StuClassServiceimp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<stu_class_result> findBywork_id(user User1) {
        String sql = "select * from user where work_id = ?";
        String sql1 = "select * from stu_class where work_id = ?";
        String sql2 = "select * from course where course_id = ?";
        user User2 = jdbcTemplate.queryForObject(sql,new user(),User1.getWork_id());
        List<stu_class> class1 = jdbcTemplate.query(sql1,new Object[]{User1.getWork_id()},new BeanPropertyRowMapper<stu_class>(stu_class.class));
        List<course> coursess = new ArrayList<course>();
        List<stu_class_result> results = new ArrayList<stu_class_result>();
        for(stu_class Class:class1){
            course course = jdbcTemplate.queryForObject(sql2,new course(),Class.getCourse_id());
            coursess.add(course);
        }
        for(int i = 0; i < class1.size();i++){
            results.add(new stu_class_result(User2,class1.get(i),coursess.get(i)));
        }
        return results;
    }

    @Override
    public List<class_result_student> findByClassnum(String classnum) {
        String sql = "select u.classnum,name,u.work_id,realname,score,avgscore,u.course_id from user natural join stu_class as u,course,classes where u.course_id = course.course_id and u.classnum = classes.classnum and u.classnum = ?";
        List<class_result_student> results = jdbcTemplate.query(sql,new Object[]{classnum},new BeanPropertyRowMapper<class_result_student>(class_result_student.class));
        return results;
    }

    @Override
    public stu_class findByWork_id(String work_id) {
        String sql = "select * from stu_class where work_id = ?";
        stu_class student = jdbcTemplate.queryForObject(sql,new stu_class(),work_id);
        return student;
    }

    @Override
    public boolean addstudentToClass(List<String> workids, String classnum) {
        boolean res = false;
        String sql1  = "select * from classes where classnum = ?";
        classes clas = jdbcTemplate.queryForObject(sql1, new classes(), classnum);
        String sql2  = "insert into stu_class (classnum, course_id, work_id, score) values (?, ?, ?, ?)";
        for(String workid: workids){
            res = jdbcTemplate.update(sql2, classnum, clas.getCourse_id(), workid, 0) > 0;
        }
        return res;
    }

    @Override
    public boolean deleteStudentByWork_idAndClassnum(String work_id, String classnum,String coursename) {
        String sql = "delete from stu_class where work_id = ? and classnum = ?";
        String sql1 = "delete from stu_node where work_id = ? and coursename = ?";
        boolean res1 = jdbcTemplate.update(sql,work_id,classnum) > 0;
        boolean res2 = jdbcTemplate.update(sql1,work_id,coursename) > 0;
        return (res1 & res2);
    }

    @Override
    public boolean deleteStudentByClassnum(String classnum,String course_id) {
        String sql = "delete from stu_class where classnum = ?";
        String sql1 = "delete from stu_node where course_id = ?";
        boolean res1 = jdbcTemplate.update(sql,classnum) > 0;
        boolean res2 = jdbcTemplate.update(sql1,classnum) > 0;
        return res1&res2;
    }
}
