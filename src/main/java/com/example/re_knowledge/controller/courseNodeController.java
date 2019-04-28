package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.course;
import com.example.re_knowledge.entry.course_node;
import com.example.re_knowledge.entry.no_table.jsmind;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.CourseService;
import com.example.re_knowledge.service.NodeService;
import com.example.re_knowledge.service.UserService;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 处理思维导图相关的url
 * url我狮子不想列举来，然后我感觉这个文件我写的注释还挺多的，自己看看吧。我写累了。
 */
@RequestMapping("/jsmind_courses")
@RestController
public class courseNodeController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private NodeService nodeService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回展示思维导图的界面
     * @param session
     * @param response
     * @param course_id
     * @return
     * @throws IOException
     */
    @GetMapping("/courses_des/{course_id}")
    public ModelAndView pageback(HttpSession session, HttpServletResponse response,@PathVariable String course_id) throws IOException{
        user user1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        session.setAttribute("course_id",course_id);
        if(user1.getStatus() == 0){
            ModelAndView model = new ModelAndView("student-course-des");
            model.addObject("student",user1);
            return model;
        }else if(user1.getStatus() == 1){
            ModelAndView model = new ModelAndView("course-des");
            model.addObject("teacher",user1);
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }

    }

    /**
     * 返回思维导图的数据
     * @param session
     * @param course_id
     * @return
     */
    @GetMapping("/courses_data/{course_id}")
    public jsmind node_data_return(HttpSession session, @PathVariable String course_id){
        List<course_node> result = nodeService.findByCourse_id(course_id);
        jsmind get_result = new jsmind();
        course course1 = courseService.findCourseByCourse_id(course_id);
        user teacher = userService.findUserByWork_id(course1.getWork_id());
        get_result.success(result,teacher.getRealname(),course1.getName());
        return get_result;
    }

    /**
     * 保存用户对思维导图做出的修改，同时需要判断该用户是否为教师
     * @param body
     * @param course_id
     * @throws IOException
     */
    @PostMapping("/courses_data/data_save/{course_id}")
    public void node_save(@RequestBody String body,@PathVariable String course_id) throws IOException{
        JSONObject json = JSONObject.fromObject(body);
        JSONArray data = JSONArray.fromObject(json.get("data"));
        nodeService.deletenodeBycourse_id(course_id);
        for(int i = 0; i < data.size(); i++){
            JSONObject node = data.getJSONObject(i);
            course_node cnode = new course_node();
            cnode.setID((String) node.get("id"));
            cnode.setParentID((String) node.get("parentid"));
            try{
                if(node.get("parentid").equals("null")){
                    cnode.setIsroot(true);
                }else{
                    cnode.setIsroot(false);
                }
            }catch (NullPointerException e1){
                cnode.setIsroot(true);
            }

            cnode.setCourse_id(course_id);
            try{
                if(!node.get("weight").equals("null")){
                    cnode.setWeight((int) node.get("weight"));
                }
            }catch (NullPointerException e1){
                cnode.setWeight(0);
            }

            cnode.setTopic((String) node.get("topic"));
            try{
                if(!node.get("description").equals("null")){
                    cnode.setDescription((String) node.get("description"));
                }
            }catch (NullPointerException e1){
                cnode.setDescription("null");
            }
            try{
                if(!node.get("answer").equals("null")){
                    cnode.setAnswer((String) node.get("answer"));
                }
            }catch (NullPointerException e1){
                cnode.setAnswer("null");
            }
            try{
                if(!node.get("question").equals("null")){
                    cnode.setQuestion((String) node.get("question"));
                }
            }catch (NullPointerException e1){
                cnode.setQuestion("null");
            }
            try{
                if(!node.get("avgscore").equals("null")){
                    cnode.setAvgscore((int) node.get("avgscore"));
                }
            }catch (NullPointerException e1){
                cnode.setAvgscore(0);
            }
            try{
                boolean res = nodeService.insertnode(cnode);
                if(res){
                    logger.info("节点:" + node.get("topic") + "保存成功!!");
                }
            }catch(MySQLIntegrityConstraintViolationException e1){
                continue;
            }

        }

    }

    /**
    * 该函数用于删除某个课程的思维导图的全部节点。
    * */

    @GetMapping("/destory/{course_id}")
    public void destory(@PathVariable String course_id,HttpSession session,HttpServletResponse response) throws IOException {
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(User1.getStatus() == 1 ){
            nodeService.deletenodeBycourse_id(course_id);
        }else{
            response.sendRedirect("/login");
        }

    }

    /**
    * 思维导图改名函数
    * */
    @GetMapping("/change_name")
    public void change_name(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
        String course_id = (String) session.getAttribute("course_id");
        courseService.updateCoursename(course_id,request.getParameter("mind_id"));
        session.removeAttribute("course_id");
        response.sendRedirect("/jsmind_courses/courses_des/" + course_id);
    }
    /**
    * 思维导图弹窗
    * */
    @GetMapping("/iframe/{course_id}/{node_id}")
    public ModelAndView iframe_page(HttpSession session,HttpServletResponse response) throws IOException {
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(User1.getStatus() == 1){
            ModelAndView model = new ModelAndView("description");
            return model;
        }else if(User1.getStatus() == 0){
            ModelAndView model = new ModelAndView("descri-for-student");
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }

    }

    /**
     * 前端iframe请求node数据
     */
    @GetMapping("/iframe_data/{course_id}/{node_id}")
    public course_node data_return(@PathVariable String course_id,@PathVariable String node_id){
        course_node result = nodeService.findNodeByCourse_idAndnodeid(course_id,node_id);
        return result;
    }
    /**
     * 前端请求保存相关设置信息
     */
    @PostMapping("/iframe_save/{course_id}/{node_id}")
    public void datasave(@PathVariable String course_id,@PathVariable String node_id,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException {
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(User1.getStatus() == 1){
            float weight = Float.valueOf(request.getParameter("weight"));
            String description = request.getParameter("description");
            boolean res = nodeService.updateNodeDes(weight,description,course_id,node_id);
            if(res){
                logger.info("课程ID为:" + course_id + "\t节点编号为:" + node_id + "信息编辑成功!");
            }else{
                logger.info("课程ID为:" + course_id + "\t节点编号为:" + node_id + "信息编辑失败!!");
            }
            response.sendRedirect("/jsmind_courses/iframe/" + course_id + "/" + node_id);
        }else {
            response.sendRedirect("/login");
        }


    }


}
