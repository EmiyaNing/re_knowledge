package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.*;
import com.example.re_knowledge.entry.no_table.Message;
import com.example.re_knowledge.entry.no_table.PostMessageForClass;
import com.example.re_knowledge.entry.no_table.UserMessage;
import com.example.re_knowledge.entry.no_table.class_result_student;
import com.example.re_knowledge.service.*;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 处理教师模式的url
 * 处理的url如下：
 *              yuming/teacherhome                                         Get                  返回教师的个人主页
 *              yuming/teacherhome                                         Post                 接受教师更改自身信息的请求
 *              yuming/teacherhome/password                                Post                 接受教师更改自身密码的请求
 *              yuming/teacherhome/upload                                  Get                  接受教师上传的个人头像
 *              yuming/teacherhome/classes                                 Get                  返回教师所有课程所有班级的大体浏览界面
 *              yuming/teacherhome/addclasses                              Post                 接受教师添加班级的请求
 *              yuming/teacherhome/deleteclass/{classnum}/{course_id}      Get                  接受教师删除班级，以及班级对应的学生的请求
 *              yuming/teacherhome/{classnum}                              Get                  返回该班级的详细数据界面
 *              yuming/teacherhome/student_data                            Get                  班级详细界面获取数据的接口
 *              yuming/teacherhome/delet_student                           Post                 从班级详细界面中删除某一位学生的函数
 *              yuming/teacherhome/user_editor                             Get                  返回教师编辑用户的界面
 *              yuming/teacherhome/user_editor_data                        Get                  用户编辑界面请求用户数据的接口
 *              yuming/teacherhome/editor_setTeacher                       Post                 该请求将某个学生设置为老师
 *              yuming/teacherhome/delete_user                             Post                 在用户编辑界面删除一个用户
 *              yuming/teacherhome/news                                    Get                  返回教师用来查看学生或其他教师留言的界面
 *              yuming/teacherhome/delete/{comment_id}                     Get                  删除一条用户的评论
 *              yuming/teacherhome/course_des                              Get                  返回展示该教师所创建的所有课程的界面
 *              yuming/teacherhome/add_course                              Get                  添加一门课程，同时为该课程添加根节点
 *              yuming/teacherhome/{course_id}/deletecourses               Get                  删除一门课程，以及对应的班级，对应的学生信息
 *
 */
@RestController
@RequestMapping("/teacherhome")
public class teacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private StuClassService stuService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NodeService nodeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回教师个人编辑主页。
     * @param session
     * @return
     */
    @GetMapping
    public ModelAndView teacher_editor(HttpSession session){
        ModelAndView model = new ModelAndView("teacher - 个人设置");
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        model.addObject("teacher",User1);
        return model;
    }

    /**
     * 教师个人主页设置个人信息界面
     * @param session
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping
    public void teacher_editor_change(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        User1.setRealname(request.getParameter("realname"));
        User1.setEmial(request.getParameter("email"));
        User1.setPhone(request.getParameter("phone"));
        userService.updUserDetial(User1);
        response.sendRedirect("/teacherhome");
    }

    /**
     * 教师个人主页设置个人密码信息。
     * @param request
     * @param session
     * @param response
     * @throws IOException
     */
    @PostMapping("/password")
    public void teacher_password_editor(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws IOException{
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        User1.setPassword(request.getParameter("password"));
        userService.updUserPassword(User1);
        response.sendRedirect("/teacherhome");
    }

    /**
     * 教师个人主页上传个人头像，图像的名称将以教师个人username命名，文件存在static/user文件夹下。
     * @param file
     * @param session
     * @return
     * @throws RuntimeException
     * @throws IOException
     * @throws JSONException
     */
    @PostMapping(value = "/upload",produces = "application/json; charset=utf-8")
    public JSONObject teacher_uploadImage(@RequestParam(value = "file")MultipartFile file, HttpSession session) throws RuntimeException, IOException, JSONException {
        JSONObject failjson = new JSONObject();
        failjson.put("src", "failed");
        if (file.isEmpty()) {
            return failjson;
        }
        String filename = file.getOriginalFilename();
        String suffixname = filename.substring(filename.lastIndexOf("."));
        String newfilename = (String) session.getAttribute("username") + suffixname;
        String Path = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/user/";
        String savePath = Path + newfilename;
        File dest = new File(savePath);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            JSONObject sussed = new JSONObject();
            sussed.put("src", "succed");
            return sussed;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return failjson;
    }

    /**
     * 返回教师的班级纵览界面，在该界面应该允许教师添加班级，删除班级，对班级的信息进行修改
     * 2018年12月17日:教师模式下的添加班级功能中应该添识别班级的创建是否合法的功能。具体打算将该功能的主要实现过程放在后端，在前端传入请求的时候检查数据库中是否存在对应的课程，如果不存在则给前端返回一个错误消息，提示课程不存在、否则给前端返回课程创建成功的json回馈。
     * @param session
     * @return
     * @throws IOException
     */
    @GetMapping("/classes")
    public ModelAndView teacher_classes(HttpSession session) throws IOException{
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        List<classes> classes = classService.findBYwork_id(User1.getWork_id());
        ModelAndView model = new ModelAndView("teacher - 我的班级");
        model.addObject("teacher",User1);
        model.addObject("classes",classes);
        return model;
    }

    @PostMapping("/addclass")
    public void teacher_addclass(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        classes class1 = new classes();
        class1.setClassnum(request.getParameter("classnum"));
        class1.setCourse_id(request.getParameter("course_id"));
        class1.setAvgscore(0);
        class1.setWork_id(teacher.getWork_id());
        boolean res = classService.addClass(class1);
        if(res){
            logger.info("班级:" + class1.getClassnum() + "添加成功!");
            response.sendRedirect("/teacherhome");
        }else{
            logger.info("班级:" + class1.getClassnum() + "添加失败!");
        }
    }

    @GetMapping("/deleteclass/{classnum}/{course_id}")
    public void teacher_deleteclass(@PathVariable String classnum,@PathVariable String course_id,HttpSession session,HttpServletResponse response) throws IOException{
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        logger.info("classnum = " + classnum + "\tcourse_id = " + course_id);
        if(teacher.getStatus() == 1){
            boolean res = classService.deleteClassByClassnumAndCourseid(classnum,course_id);
            boolean res2 = stuService.deleteStudentByClassnum(classnum,course_id);
            if(res&res2){
                response.sendRedirect("/teacherhome");
            }else{
                response.sendRedirect("/login");
            }
        }else{
            response.sendRedirect("/login");
        }

    }


    /**
     * 进入显示班级每个成员成绩的信息界面。
     * @param session
     * @param classnum
     * @return
     */
    @GetMapping("/{classnum}")
    public ModelAndView teacher_classes_detail(HttpSession session, @PathVariable String classnum){
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        session.setAttribute("classnum",classnum);
        ModelAndView model = new ModelAndView("teacher - 班级成绩");
        model.addObject("teacher",User1);
        return model;
    }

    /**
     * 班级成员详细信息界面的请求学生数据信息的接口。
     * @param session
     * @param request
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/student_data")
    public PostMessageForClass teacher_classes_detail_data(HttpSession session,HttpServletRequest request) throws InterruptedException {
        PostMessageForClass message = new PostMessageForClass();
        Thread.sleep(100);
        String classnum = (String) session.getAttribute("classnum");
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        List<class_result_student> results = stuService.findByClassnum(classnum);
        session.removeAttribute("classnum");
        message.success(results);
        return message;
    }

    /**
     * 删除学生的接口。
     * @param request
     * @param session
     */
    @PostMapping("/delete_student")
    public void delete_student_class(HttpServletRequest request,HttpSession session){
        String classnum = request.getParameter("classnum");
        String work_id = request.getParameter("work_id");
        String coursename = request.getParameter("coursename");
        boolean res = stuService.deleteStudentByWork_idAndClassnum(work_id,classnum,coursename);
        //可以考虑根据更新结果返回结果
    }

    /**
     * 返回对用户界面进行编辑的界面。
     * @param session
     * @return
     */
    @GetMapping("/user_editor")
    public ModelAndView user_editor_page(HttpSession session){
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        ModelAndView model = new ModelAndView("teacher - 用户设置");
        model.addObject("teacher",teacher);
        return model;
    }
    //返回界面
    @GetMapping("/adduser")
    public ModelAndView page_add_user(HttpSession session,HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            ModelAndView model = new ModelAndView("add_user");
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }
    }
    //添加用户
    @PostMapping("/adduser")
    public void add_user(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            user User1 = new user();
            User1.setStatus(Integer.valueOf(request.getParameter("status")));
            User1.setPassword(request.getParameter("password"));
            User1.setUsername(request.getParameter("username"));
            User1.setPhone(request.getParameter("phone"));
            User1.setEmial(request.getParameter("email"));
            User1.setRealname(request.getParameter("realname"));
            User1.setWork_id(request.getParameter("work_id"));
            userService.addUser(User1);
        }else{
            response.sendRedirect("/login");
        }

    }

    //数据接口
    @GetMapping("/user_editor_data")
    public UserMessage user_editor_data(HttpServletRequest request,HttpSession session){
        List<user> users = userService.findAll();
        UserMessage message = new UserMessage();
        message.success(users);
        return message;
    }

    @GetMapping("/editor_user/{work_id}")
    public ModelAndView page_editor_user(HttpSession session, HttpServletResponse response,@PathVariable String work_id) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            ModelAndView model = new ModelAndView("editor-user");
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }

    }

    /**
     * 修改学生信息的接口，首先判断执行该操作的用户的身份，如果是教师允许执行该操作，否则跳转至登陆界面。
     * 修改学生的信息时，先将原用户的信息删除掉，然后再将新用户的信息添加进去。
     * 执行该操作使用的界面和添加用户使用的界面是一致的。
     * @param work_id
     * @param request
     * @param session
     * @param response
     */
    @PostMapping("/editor_user/{work_id}")
    public void editor_User(@PathVariable String work_id,HttpServletRequest request,HttpSession session, HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            userService.delByWork_id(work_id);
            user User1 = new user();
            User1.setStatus(Integer.valueOf(request.getParameter("status")));
            User1.setPassword(request.getParameter("password"));
            User1.setUsername(request.getParameter("username"));
            User1.setPhone(request.getParameter("phone"));
            User1.setEmial(request.getParameter("email"));
            User1.setRealname(request.getParameter("realname"));
            User1.setWork_id(request.getParameter("work_id"));
            userService.addUser(User1);
            response.sendRedirect("/teacherhome/user_editor_data");
        }else{
            response.sendRedirect("/login");
        }
    }

    @GetMapping("/get_user/{work_id}")
    public user return_an_user(@PathVariable String work_id,HttpSession session,HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            user User1 = userService.findUserByWork_id(work_id);
            return User1;
        }else{
            response.sendRedirect("/login");
            return null;
        }
    }
    //删除用户
    @PostMapping("/delete_user")
    public void delete_user(HttpServletRequest request){
        String work_id = request.getParameter("work_id");
        userService.delByWork_id(work_id);
    }

    /**
     * 消息编辑界面
     * @param session
     * @return
     * @throws NullPointerException
     */
    @GetMapping("/news")
    public ModelAndView news(HttpSession session) throws NullPointerException{
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        List<course> courses = courseService.findCourseByWork_id(teacher.getWork_id());
        List<comment> comments = new ArrayList<comment>();
        for (int i = 0; i < courses.size(); i++){
            List<comment> result = commentService.findBycourse_id(courses.get(i).getCourse_id());
            if(result != null){
                comments.addAll(result);
            }
        }
        logger.info("评论数量为:" + comments.size());
        ModelAndView model = new ModelAndView("teacher - 消息中心");
        model.addObject("teacher",teacher);
        model.addObject("comments",comments);
        return model;
    }
    //教师用来删除评论的接口
    @GetMapping("/news/{comment_id}")
    public void delete_news(HttpSession session,@PathVariable String comment_id){
        boolean res = commentService.deletebycommentid(comment_id);
        if(res){
            logger.info("成功删除id为:" + comment_id + "的评论");
        }else{
            logger.info("评论id为:" + comment_id + "的评论删除失败!!");
        }
    }

    /**
     * 教师课程展示相关界面。
     */
    @GetMapping("/courses_des")
    public ModelAndView course(HttpSession session,HttpServletRequest request){
        ModelAndView model = new ModelAndView("teacher-course");
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        List<course> courses = courseService.findCourseByWork_id(teacher.getWork_id());
        model.addObject("teacher",teacher);
        model.addObject("courses",courses);
        return model;
    }

    @PostMapping("/addcourse")
    public void teacher_addcourse(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException, MySQLIntegrityConstraintViolationException {
        user teacher       = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        String name        = request.getParameter("course_name");
        String course_id   = request.getParameter("course_id");
        String description = request.getParameter("description");
        course_node root   = new course_node();
        root.setIsroot(true);
        root.setID(course_id + "root");
        root.setWeight(1);
        root.setAvgscore(0);
        root.setCourse_id(course_id);
        root.setTopic(name);
        if(teacher.getStatus() == 1){
            boolean res  = courseService.addCoursename(course_id,name,teacher.getWork_id(),description);
            boolean res2 = nodeService.insertnode(root);
            if(res & res2){
                logger.info("课程:" + name + "添加成功!!");
                response.sendRedirect("/teacherhome");
            }else{
                logger.info("课程:" + name + "添加失败??");
                response.sendRedirect("/teacherhome");
            }
        }else{
            response.sendRedirect("/login");
        }
    }

    @GetMapping("/{course_id}/deletecourse")
    public void teacher_deletecourse(HttpSession session,@PathVariable String course_id,HttpServletResponse response) throws IOException{
        user teacher       = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            boolean res    = courseService.deleteByCourse_id(course_id);
            if(res){
                logger.info("课程:" + course_id + "删除成功!!");
                response.sendRedirect("/teacherhome");
            }else{
                logger.info("课程:" + course_id + "删除失败??");
                response.sendRedirect("/teacherhome");
            }
        }else{
            response.sendRedirect("/login");
        }
    }

    @GetMapping("/addstudent/{classnum}")
    public ModelAndView addstudent_page(HttpSession session, HttpServletResponse response) throws IOException {
        user teacher       = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        ModelAndView model = new ModelAndView("add_student");
        if(teacher.getStatus() == 1){
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }
    }

    @PostMapping("/addstudent/{classnum}")
    public Message addstudent_to_mysql(HttpSession session, HttpServletResponse response, @RequestBody List<String> userworkid, @PathVariable String classnum) throws IOException {
        user teacher       = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        Message result     = new Message();
        if(teacher.getStatus() != 1){
            result.fail();
            response.sendRedirect("/login");
            return result;
        }
        boolean res = stuService.addstudentToClass(userworkid,classnum);
        for(int i = 0; i < userworkid.size(); i++){
            logger.info(userworkid.get(i));
        }
        if(res){
            logger.info("添加成功!!");
        }else{
            logger.info("添加失败!!");
        }
        result.success();
        return result;
    }

    @GetMapping("/logout")
    public void teacher_logout(HttpServletResponse response,HttpSession session) throws IOException {
        response.sendRedirect("/login");
    }
}
