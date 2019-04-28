package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.classes;

import java.util.List;

public interface ClassService {
    /*
    * 通过教师id来查找班级
    * */
    public List<classes> findBYwork_id(String work_id);
    public boolean addClass(classes class1);
    public boolean deleteClassByClassnumAndCourseid(String classnum,String course_id);
    
}
