package com.example.re_knowledge.service;

import com.example.re_knowledge.entry.comment;

import java.util.List;

public interface CommentService {
    public List<comment> findBywork_id(String work_id);
    public List<comment> findBycourse_id(String course_id);
    public boolean updaterealnameBywork_id(String work_id);
    public boolean deletebycommentid(String comment_id);
    public boolean add_comment(comment comment);
}
