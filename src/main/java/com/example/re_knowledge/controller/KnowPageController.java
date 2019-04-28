package com.example.re_knowledge.controller;


import com.example.re_knowledge.entry.comment;
import com.example.re_knowledge.entry.no_table.file_data;
import com.example.re_knowledge.entry.no_table.file_name;
import com.example.re_knowledge.entry.no_table.markbuffer;
import com.example.re_knowledge.entry.no_table.upload_return_json;
import com.example.re_knowledge.entry.user;
import com.example.re_knowledge.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/knowledge")
@RestController
public class KnowPageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ClassService classService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private StuClassService stuService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{course_id}/{node_id}")
    public ModelAndView page_return(@PathVariable String course_id, @PathVariable String node_id,HttpSession session, HttpServletResponse response) throws IOException {
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        List<comment> comms= commentService.findBycourse_id(course_id);
        if(User1.getStatus() == 1){
            markbuffer remark = new markbuffer();
            StringBuffer buffer = new StringBuffer();
            File mark  = new File("/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/markdown/" + course_id + '#' + node_id + ".md");
            if(mark.exists()){
                FileInputStream markinput = new FileInputStream(mark);
                InputStreamReader reader  = new InputStreamReader(markinput,"UTF-8");
                while(reader.ready()){
                    buffer.append((char) reader.read());
                }
                reader.close();
                markinput.close();
                logger.info(String.valueOf(buffer));
                remark.setBuffer(String.valueOf(buffer));
            }else{
                buffer = null;
                remark.setBuffer(String.valueOf(buffer));
            }
            ModelAndView model = new ModelAndView("Teach_details");
            model.addObject("teacher", User1);
            model.addObject("comments",comms);
            model.addObject("markfile",remark);
            return model;
        }else if (User1.getStatus() == 0){
            markbuffer remark = new markbuffer();
            StringBuffer buffer = new StringBuffer();
            File mark  = new File("/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/markdown/" + course_id + '#' + node_id + ".md");
            if(mark.exists()){
                FileInputStream markinput = new FileInputStream(mark);
                InputStreamReader reader  = new InputStreamReader(markinput,"UTF-8");
                while(reader.ready()){
                    buffer.append((char) reader.read());
                }
                reader.close();
                markinput.close();
                logger.info(String.valueOf(buffer));
                remark.setBuffer(String.valueOf(buffer));
            }else{
                buffer = null;
                remark.setBuffer(String.valueOf(buffer));
            }
            ModelAndView model = new ModelAndView("Study_details");
            model.addObject("student", User1);
            model.addObject("comments",comms);
            model.addObject("markfile",remark);
            return model;
        }else{
            response.sendRedirect("/login");
            return null;
        }
    }

    @PostMapping("/comment_add/{course_id}/{node_id}")
    public void add_comment(HttpSession session, HttpServletRequest request,@PathVariable String course_id, @PathVariable String node_id, HttpServletResponse response) throws IOException {
        Calendar calendar= Calendar.getInstance();
        Date     time    = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataNowstr= sdf.format(time);
        logger.info(dataNowstr);
        comment comment1 = new comment();
        user User1       = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        comment1.setWork_id(User1.getWork_id());
        comment1.setTime(dataNowstr);
        comment1.setRealname(User1.getRealname());
        comment1.setComment(request.getParameter("comment"));
        comment1.setCourse_id(course_id);
        comment1.setComment_id(User1.getUsername()+"的评论");
        commentService.add_comment(comment1);
        response.sendRedirect("/knowledge/" + course_id + "/" + node_id);
    }


    @PostMapping("/markdown/{course_id}/{node_id}")
    public void markdown_upload(@PathVariable String course_id, @PathVariable String node_id, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            BufferedReader br = request.getReader();
            String        str = "";
            String     buffer = "";
            File mark         = new File("/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/markdown/" + course_id + '#' + node_id + ".md");
            if(!mark.exists()){
                logger.info("文件不存在，直接存取");
                mark.createNewFile();
                FileOutputStream markoutput = new FileOutputStream(mark);
                OutputStreamWriter writer   = new OutputStreamWriter(markoutput, "UTF-8");
                while((str = br.readLine()) != null){
                    buffer += str;
                    buffer += "\r\n";
                }
                writer.write(buffer);
                writer.close();
                markoutput.close();
            }else{
                logger.info("文件存在，先删除再创建");
                mark.delete();
                mark.createNewFile();
                FileOutputStream markoutput = new FileOutputStream(mark);
                OutputStreamWriter writer   = new OutputStreamWriter(markoutput, "UTF-8");
                while((str = br.readLine()) != null){
                    buffer += str;
                    buffer += "\r\n";
                }
                writer.write(buffer);
                writer.close();
                markoutput.close();
            }
        }else{
            response.sendRedirect("/login");
        }
    }

    @GetMapping("/deletecomment/{comment_id}")
    public void delete_comment(@PathVariable String comment_id, HttpSession session, HttpServletResponse response) throws IOException {
        user teacher = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(teacher.getStatus() == 1){
            commentService.deletebycommentid(comment_id);
            response.sendRedirect("/teacherhome");
        }else{
            response.sendRedirect("/login");
        }
    }

    @PostMapping("/video/{course_id}/{node_id}")
    public void video_upload(@PathVariable String course_id, @PathVariable String node_id,@RequestParam("file") MultipartFile file, HttpSession session, HttpServletResponse response) throws IOException {
        try{
            if(file.isEmpty()){
                logger.info("文件为空!!");
                response.sendRedirect("/login");
            }
            String fileOriginal   = file.getOriginalFilename();
            String suffixName     = fileOriginal.substring(fileOriginal.lastIndexOf("."));
            String videoName      = course_id + "#" + node_id + "." + suffixName;
            File   file_origin    = new File("/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/video/" + videoName);
            file_origin.delete();
            String path           = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/video/" + videoName;
            File   dest           = new File(path);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            logger.info("上传成功!");
        }catch (IllegalStateException e){
            e.printStackTrace();;
        }
    }


    @GetMapping("/video_download/{course_id}/{node_id}")
    public void video_download(@PathVariable String course_id, @PathVariable String node_id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String filepath = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/video/" + course_id + '#' + node_id + ".mp4";
        File file = new File(filepath);
        if(file.exists()){
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + course_id + '#' + node_id + ".mp4");
            byte[] buffer = new byte[1024];
            FileInputStream     fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream        os  = response.getOutputStream();
            int                 i   = bis.read(buffer);
            while(i != -1){
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            logger.info("视频下载成功");
        }
    }

    /**
     * 文件上传下载借口，需要实现查看当前知识节点上教师上传的所有文件，并允许教师用户随意的添加文件或者删除文件，而学生用户只允许下载文件。
     * @param course_id
     * @param node_id
     * @param file
     * @param session
     * @param response
     * @throws IOException
     */

    @PostMapping("/ppt/{course_id}/{node_id}")
    public upload_return_json ppt_upload(@PathVariable String course_id, @PathVariable String node_id, @RequestParam("file") MultipartFile file, HttpSession session, HttpServletResponse response) throws IOException {
        upload_return_json result = new upload_return_json();
        try{
            if(file.isEmpty()){
                logger.info("文件为空!!");
                response.sendRedirect("/knowledge/" + course_id + '/' + node_id);
            }
            String fileOriginal       = file.getOriginalFilename();
            String suffixName         = fileOriginal.substring(fileOriginal.lastIndexOf("."));
            String file_name          = course_id + '-' + node_id + suffixName;
            File   file_origin        = new File("/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/ppt/" + file_name);
            file_origin.delete();
            String path               = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/ppt/" + file_name;
            File   dest               = new File(path);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
            result.success();
            response.sendRedirect("/knowledge/" + course_id + "/" + node_id);
            logger.info("上传成功!");
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前ppt文件夹内的所有文件，然后遍历所有文件名，如果文件不符合定义（也就是该文件不属于该节点），则将文件从
     * 结果集中删除。该接口允许学生和教师同时访问。
     * @param course_id
     * @param node_id
     * @param request
     * @param session
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/ppt_filedata/{course_id}/{node_id}")
    public file_data file_list(@PathVariable String course_id,@PathVariable String node_id,HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        user User1 = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        if(User1.getStatus() != 1){
            response.sendRedirect("/login");
        }
        String path                = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/ppt/";
        File   dirfile             = new File(path);
        List<String> files         = Arrays.asList(dirfile.list());       //获取当前文件目录下所有文件的名称
        List<file_name> filelist   = new ArrayList<file_name>();
        file_data       result     = new file_data();
        if(files.isEmpty()){
            return null;
        }else{
            for(int i = 0; i < files.size(); i++){
                //接下来将file变量的前缀提取出来，符合我定义的文件命名规则就将其留下来，否则将其从该list中删除，最后返回结果list。
                String file            = files.get(i);
                String file_head       = file.substring(0,file.lastIndexOf("."));
                if(file_head.equals(course_id + "-" + node_id)){
                    file_name tempfile = new file_name(file);
                    filelist.add(tempfile);
                }
            }
            result.success(filelist);
            return result;
        }
    }

    /**
     * @PathVariable 标签会默认分割# ？ / 等符号，并取分割的前半段作为参数。。
     * @param file_head
     * @param file_tail
     * @param session
     * @param response
     * @throws IOException
     */
    @GetMapping("/ppt_del/{file_head}/{file_tail}")
    public void ppt_del(@PathVariable String file_head, @PathVariable String file_tail, HttpSession session, HttpServletResponse response) throws IOException {
        user   User1     = userService.findUserByWork_id((String) session.getAttribute("work_id"));
        String path      = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/ppt";
        String file_name = file_head + "." + file_tail;
        File   file      = new File(path  + "/" + file_name);
        if(User1.getStatus() != 1){
            response.sendRedirect("/login");
        }
        logger.info(String.valueOf(file.isFile()));
        if(file.isFile() && file.exists()){
            file.delete();
            logger.info(file_name + "删除成功");
        }

    }

    @GetMapping("/ppt_download/{file_head}/{file_tail}")
    public void ppt_download(@PathVariable String file_head, @PathVariable String file_tail, HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filepath           = "/Users/ningkangl/Documents/学园都市/re_knowledge/src/main/resources/static/ppt/" + file_head + "." + file_tail;
        File   file               = new File(filepath);
        if(file.exists()){
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + file_head + "." + file_tail);
            byte[] buffer = new byte[1024];
            FileInputStream     fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream        os  = response.getOutputStream();
            int                 i   = bis.read(buffer);
            while(i != -1){
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            logger.info("ppt下载成功！！");
        }
    }

}
