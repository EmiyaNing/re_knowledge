package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.no_table.class_result_student;
import com.example.re_knowledge.entry.no_table.stu_class_result;
import com.example.re_knowledge.entry.stu_class;
import com.example.re_knowledge.entry.user;

import java.util.List;

public interface StuClassService {
    public List<stu_class_result> findBywork_id(user User1);
    public List<class_result_student> findByClassnum(String classnum);
    public stu_class findByWork_id(String work_id);
    public boolean addstudentToClass(List<String> workids,String classnum);
    public boolean deleteStudentByWork_idAndClassnum(String work_id, String classnum,String coursename);
    public boolean deleteStudentByClassnum(String classnum,String course_id);
}
