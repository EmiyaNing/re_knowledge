package com.example.re_knowledge.service.imp;

import com.example.re_knowledge.entry.comment;
import com.example.re_knowledge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceimp implements CommentService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CommentServiceimp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<comment> findBywork_id(String work_id) {
        String sql = "select * from commet where work_id = ?";
        List<comment> result = jdbcTemplate.query(sql,new Object[]{work_id},new BeanPropertyRowMapper<comment>(comment.class));
        return result;
    }

    @Override
    public List<comment> findBycourse_id(String course_id) {
        String sql = "select * from commet where course_id = ?";
        List<comment> result = jdbcTemplate.query(sql,new Object[]{course_id},new BeanPropertyRowMapper<comment>(comment.class));
        return result;
    }

    @Override
    public boolean updaterealnameBywork_id(String work_id) {
        return false;
    }

    @Override
    public boolean deletebycommentid(String comment_id) {
        String sql = "delete from commet where comment_id = ?";
        boolean res = jdbcTemplate.update(sql,comment_id) > 0;
        return res;
    }

    @Override
    public boolean add_comment(comment comment) {
        String sql = "insert into commet(comment_id,course_id,time,realname,work_id,comment) values (?,?,?,?,?,?)";
        boolean res = jdbcTemplate.update(sql, comment.getComment_id(), comment.getCourse_id(), comment.getTime(),
                                                comment.getRealname(), comment.getWork_id(), comment.getComment()) > 0;
        return res;
    }
}
