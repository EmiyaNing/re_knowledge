package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.course;

import java.util.List;

public interface CourseService {
    public List<course> findCourseByWork_id(String work_id);
    public course findCourseByCourse_id(String course_id);
    public boolean updateCoursename(String course_id,String name);
    public boolean addCoursename(String course_id,String name,String work_id,String description);
    public boolean deleteByCourse_id(String course_id);
}
