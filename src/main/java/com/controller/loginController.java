package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.Teacher;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class loginController {
    @Autowired
    @Qualifier("teacher")
    private TeacherService teaService;

    @RequestMapping("/role")
    public String role(){
        return "role_select";
    }

    //角色选择
    @GetMapping("/teacher/login")
    public String teaLogin( Model model){
        return "teacher/login_teacher";
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "role_select";
    }
    //登录提交
    @PostMapping("/teacher/login")

    public String teaLoginPost(@RequestParam("number") String number, @RequestParam("password") String password, Model model, HttpServletRequest request,RedirectAttributes attributes){
        Teacher teacher=teaService.yanzheng(number,password);
        if(teacher!=null){
            HttpSession session=request.getSession();
            session.setAttribute("user",teacher);
            session.setAttribute("userName",teacher.getName());
            attributes.addFlashAttribute("userName",teacher.getName());
            return "redirect:/index";
        }else {
            model.addAttribute("msg","用户名或者密码错误！");
            return "/teacher/login_teacher";
        }
    }

    @RequestMapping("/index")
    public ModelAndView index(@ModelAttribute("userName") String userName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName", userName);
        // 封装返回结果到Model中
        // 设置返回的视图名称
        modelAndView.setViewName("index");
        return modelAndView;
    }
    //老师注册
    @RequestMapping("/teacher/register")
    public String teaRegister(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("phone") String phone,@RequestParam("number") String number,@RequestParam("password") String password,@RequestParam("status") String status,Model model){
        int sta=Integer.parseInt(status);
        Teacher t=new Teacher(number,password,name,email,phone,sta);
        if(teaService.addTeacher(t)) {
            return "/teacher/login_teacher";
        }else {
            model.addAttribute("msg","学工号已存在");
            return "/teacher/login_teacher";
        }
    }

    //教师完善个人信息
    @GetMapping( "/teacher/updateInfo" )
    public String updateStudentInfo(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        Teacher teacher= (Teacher) session.getAttribute("user");
        String number=teacher.getNumber();
        if(number != null){
            Teacher teacher2=teaService.queryTeacherByNumber(number);
            model.addAttribute("teacher", teacher2);
        }
        return "/teacher/tea_updateInfo";
    }


    @PostMapping( "/teacher/updateInfo2")
    @ResponseBody
    public String updateTeacherInfo(@RequestBody Teacher teacher) throws IOException {
        teaService.updateTeacherInfo(teacher);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("msg","修改成功！");
        //jackson ObjectMapper
        ObjectMapper mapper= new ObjectMapper();
        String str= mapper.writeValueAsString(map);
        return str;
    }

    @RequestMapping("/teacher/queryInfo")
    public String queryTeacherInfo(HttpServletRequest request,Model model){
        HttpSession session=request.getSession();
        Teacher teacher= (Teacher) session.getAttribute("user");
        Teacher findTeacher=teaService.queryTeacherByNumber(teacher.getNumber());
        model.addAttribute("teacher",findTeacher);
        return "/teacher/tea_queryInfo";
    }

    //修改密码
    @GetMapping("/teacher/changePw0")
    public String changePw(){
        return "/teacher/tea_changePw";
    }

    @PostMapping("/teacher/changePw")
    @ResponseBody
    public String  changePw(String number ,String OldPassword,String NewPassword){
        String msg = teaService.updateTeacherPwd(number,OldPassword,NewPassword);
        return msg;
    }


    //发送邮件
    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String number,String email){
        return teaService.sendEmail(number,email);
    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
