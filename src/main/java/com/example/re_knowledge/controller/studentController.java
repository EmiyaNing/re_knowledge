package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.comment;
import com.example.re_knowledge.entry.no_table.class_result_student;
import com.example.re_knowledge.entry.no_table.stu_class_result;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.StuClassService;
import com.example.re_knowledge.service.UserService;
import com.example.re_knowledge.service.imp.CommentServiceimp;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 处理学生界面的对应url，学生界面的主url为/studenthome
 * url：
 *          yuming/studenthome                                  Get               返回学生的个人信息编辑主页
 *          yuming/studenthome/personal_information             Post              接受学生的更改自身个人信息的请求
 *          yuming/studenthome/personal_password                Post              接受学生的更改密码的请求
 *          yuming/studenthome/upload                           Post              接受学生上传个人头像的请求
 *          yuming/studenthome/score                            Get               返回学生的个人成绩信息的详细界面
 *          yuming/studenthome/{classnum}                       Get               返回该学生所在班级的所有学生的成绩的界面
 *          yuming/studenthome/news                             Get               返回该学生留下的所有留言的信息
 */
@RestController
@RequestMapping("/studenthome")
public class studentController {
    @Autowired
    private UserService userService;
    @Autowired
    private StuClassService stuClassService;
    @Autowired
    private CommentServiceimp commentServiceimp;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView home(HttpSession session){
        ModelAndView model = new ModelAndView("student - 个人设置");
        user User1 = userService.findUserByUsername((String) session.getAttribute("username"));
        model.addObject("student",User1);
        return model;
    }

    @PostMapping("/personal_information")
    public void personal_information_editor(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
        user User1 = userService.findUserByUsername((String) session.getAttribute("username"));
        User1.setRealname(request.getParameter("realname"));
        User1.setPhone(request.getParameter("phone"));
        User1.setEmial(request.getParameter("email"));
        boolean flag = userService.updUserDetial(User1);
        if(flag){
            logger.info("学生用户:" + User1.getWork_id() + "\t姓名，电话，邮箱信息修改成功!!!");
            response.sendRedirect("/studenthome");
        }else{
            logger.info("学生用户:" + User1.getWork_id() + "\t信息修改失败!!!");
            response.sendRedirect("/studenthome");
        }
    }

    @PostMapping("/personal_password")
    public void personal_password_editor(HttpSession session,HttpServletRequest request,HttpServletResponse response)throws IOException{
        user User1 = userService.findUserByUsername((String) session.getAttribute("username"));
        User1.setPassword(request.getParameter("password"));
        boolean flag = userService.updUserPassword(User1);
        if(flag){
            logger.info("～～～～～～学生用户:" + User1.getWork_id() + "\t密码信息修改成功!!～～～～～～～");
            response.sendRedirect("/studenthome");
        }else{
            logger.info("----------学生用户" + User1.getWork_id() + "\t密码信息修改失败!!--------------");
            response.sendRedirect("/studenthome");
        }
    }

    @PostMapping(value = "/upload",produces = "application/json; charset=utf-8")
    public JSONObject uploadImage(@RequestParam(value = "file")MultipartFile file,HttpSession session) throws RuntimeException, IOException, JSONException {
        JSONObject failjson = new JSONObject();
        failjson.put("src", "failed");
        if (file.isEmpty()) {
            return failjson;
        }
        String filename = file.getOriginalFilename();
        System.out.println("上传文件名为:" + filename);
        String suffixname = filename.substring(filename.lastIndexOf("."));
        System.out.println("文件后缀名为:" + suffixname);
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
            System.out.println("上传成功后的文件路径未：" + Path + newfilename);
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

    @GetMapping("/score")
    public ModelAndView student_score(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
        ModelAndView model = new ModelAndView("student - 我的学习");
        user User1 = userService.findUserByUsername((String) session.getAttribute("username"));
        List<stu_class_result> results = stuClassService.findBywork_id(User1);
        model.addObject("student",User1);
        model.addObject("results",results);
        return model;
    }

    @GetMapping("/{classnum}")
    public ModelAndView student_class(HttpSession session,@PathVariable String classnum) throws IOException{
        ModelAndView model = new ModelAndView("student - 我的班级");
        logger.info("返回学生查询的班级页面");
        user User1 = userService.findUserByUsername((String) session.getAttribute("username"));
        List<class_result_student> result = stuClassService.findByClassnum(classnum);
        model.addObject("students",result);
        model.addObject("student",User1);
        return model;
    }

    @GetMapping("/news")
    public ModelAndView student_new(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
        ModelAndView model = new ModelAndView("student -消息中心");
        logger.info("返回学生消息界面");
        List<comment> comments = commentServiceimp.findBywork_id((String) session.getAttribute("work_id"));
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        model.addObject("student",User1);
        model.addObject("comments",comments);
        return model;

    }


}
