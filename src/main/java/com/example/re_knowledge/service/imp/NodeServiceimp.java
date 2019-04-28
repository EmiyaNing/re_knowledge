package com.example.re_knowledge.service.imp;

import com.example.re_knowledge.entry.course_node;
import com.example.re_knowledge.service.NodeService;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class NodeServiceimp implements NodeService{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NodeServiceimp(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<course_node> findByCourse_id(String course_id) {
        String sql = "select * from course_node where course_id = ?";
        List<course_node> result = jdbcTemplate.query(sql,new Object[]{course_id},new BeanPropertyRowMapper<course_node>(course_node.class));
        return result;
    }

    @Override
    public boolean insertnode(course_node node) throws MySQLIntegrityConstraintViolationException {
        String sql = "insert into course_node (ID,parentID,isroot,weight,course_id,topic,description,answer,question,avgscore) value (?,?,?,?,?,?,?,?,?,?)";
        try{
            boolean res = jdbcTemplate.update(sql,node.getID(),node.getParentid(),node.getIsroot(),node.getWeight(),node.getCourse_id(),node.getTopic(),node.getDescription(),node.getAnswer(),node.getQuestion(),node.getAvgscore()) > 0;
            return res;
        }catch (DuplicateKeyException e1){
            throw new MySQLIntegrityConstraintViolationException(e1.getMessage());
        }

    }

    @Override
    public boolean deletenodeBycourse_id(String course_id) {
        String sql = "delete from course_node where course_id = ?";
        boolean res = jdbcTemplate.update(sql,course_id) > 0;
        return res;
    }

    @Override
    public course_node findNodeByCourse_idAndnodeid(String course_id, String nodeid) {
        String sql = "select * from course_node where course_id = ? and ID = ?";
        course_node result = jdbcTemplate.queryForObject(sql,new course_node(),course_id,nodeid);
        return result;
    }

    @Override
    public boolean updateNodeDes(float weight, String description,String course_id,String nodeid) {
        String sql = "update course_node set weight = ?,description = ? where course_id = ? and ID = ?";
        boolean res = jdbcTemplate.update(sql,weight,description,course_id,nodeid) > 0;
        return res;
    }
}
