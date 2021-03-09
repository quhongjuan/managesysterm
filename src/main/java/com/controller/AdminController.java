package com.controller;

import com.pojo.Exampapers;
import com.pojo.Teacher;
import com.service.PaperService;
import com.service.TeacherService;
import com.utils.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    @Qualifier("teacher")
    private TeacherService teacherService;
    @Autowired
    @Qualifier("exam")
    private PaperService paperService;

    //查看教师列表
    @RequestMapping("/admin/queryTeachers")
    public String queryAllTeacher(HttpServletRequest request,Model model ){
        //管理员标志
        boolean flag=false;
         flag = admin.isAdmin((Teacher) request.getSession().getAttribute("user"));
         if(flag){
             List<Teacher>  teachers = teacherService.queryAllTeacher();
             model.addAttribute("list",teachers);
             model.addAttribute("isAdmin","1");
         }else{
             model.addAttribute("isAdmin","0");
         }
     return "/admin/teacherList";
    }

    /**
     * 删除教师
     */
    @RequestMapping("/admin/delete/{teacherId}")
    public  String  deleteTeacher(@PathVariable("teacherId") int teacherId){
        teacherService.deleteTeacher( teacherId);
        return "forward:/admin/queryTeachers";
    }

    /**
     * 查看详情
     */
    @RequestMapping("/admin/detail/{teacherId}")
    public String teacherDetail(@PathVariable("teacherId") int teacherId,Model model){
        Teacher teacher = teacherService.queryTeacherById(teacherId);
        List<Exampapers> exampapers = paperService.queryAllEaxmPaper(teacherId);
        model.addAttribute("teacher",teacher);
        model.addAttribute("exampapers",exampapers);
        return "/admin/detailInfo";
    }
    @RequestMapping("/pdf3")
    public void queryPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        System.out.println("id="+id);
        Optional<Exampapers> exampapers= Optional.ofNullable(paperService.queryPaperByPaperId(id));
        if(exampapers.isPresent()){
            byte[] fileBytes=exampapers.get().getContext();
            response.getOutputStream().write(fileBytes);
        }
    }

    @RequestMapping("/admin/paperDelete")
    public String delete( int paperId, int teacherId){
        System.out.println("paperId=="+paperId+"teacher=="+teacherId);
        paperService.deletePaper(paperId);
        return "forward:/admin/detail/"+teacherId;
    }

}
