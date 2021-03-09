package com.controller;

import com.pojo.Exampapers;
import com.pojo.Teacher;
import com.service.PaperService;
import com.service.TeacherService;
import com.utils.createString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.utils.time.transformTime;

@Controller
public class examController {
    @Autowired
    @Qualifier("exam")
    private PaperService paperService;
    @Autowired
    @Qualifier("teacher")
    private TeacherService teacherService;


    //上传试卷
    @GetMapping("/add")
    public  String add(){
        return "exam/release_paper";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload( MultipartFile context,  String title, String releaseTime,String releaseTime2, String endTime,String endTime2, String stuCode, String teaCode,  HttpServletRequest request) throws IOException, ParseException {

        int id=0;
        //获得authorId  后面修补
        HttpSession session=request.getSession();
        Teacher teacher= (Teacher) session.getAttribute("user");
        int authorId=teacher.getId();
        //文件
        byte[] context0=context.getBytes();

       String startTime = transformTime(releaseTime,releaseTime2);
       String overTime = transformTime(endTime,endTime2);
       Exampapers exampapers=new Exampapers( authorId,startTime, overTime, context0,  stuCode,  teaCode,  0,  title);
        id=paperService.addPaper(exampapers);
        return "添加成功，请到“查看试卷”中查看";
    }

    //生出码
    @PostMapping("/createString")
    @ResponseBody
    public String createString(){
        return createString.createRedonString();
    }


    //查看list
    @RequestMapping("/find")
    public  String  findPaperList(HttpServletRequest request,Model model){
        String  name= (String) request.getSession().getAttribute("userName");
        System.out.println("用户名字："+name);
        Teacher teacher= (Teacher) request.getSession().getAttribute("user");
        List<Exampapers> list=paperService.queryAllEaxmPaper(teacher.getId());
        //List<Exampapers> list=paperService.queryAllEaxmPaper(2);
        model.addAttribute("list",list);
        model.addAttribute("authorName",name);
        return "exam/paperList";
    }

    //查看详情
    @RequestMapping("/exam/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model){
        Exampapers exampapers = paperService.findDetail(id);
        System.out.println("detail--id:"+exampapers.getAuthorId());
        String authorName =teacherService.queryTeacherById(exampapers.getAuthorId()).getName();
        model.addAttribute("exampapers",exampapers);
        model.addAttribute("authorName",authorName);
        return "/exam/detail_paper";
    }

    @RequestMapping("/pdf")
    public void queryPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        System.out.println("id="+id);
        Optional<Exampapers> exampapers= Optional.ofNullable(paperService.queryPaperByPaperId(id));
        if(exampapers.isPresent()){
            byte[] fileBytes=exampapers.get().getContext();
            response.getOutputStream().write(fileBytes);
        }
    }

    @GetMapping("/exam/change/{id}")
    public String change01(@PathVariable("id") int id, Model model){
        Exampapers exampapers=paperService.queryPaperByPaperId(id);
        model.addAttribute("exampapers",exampapers);
        return "/exam/change_paper";
    }

    @PostMapping("/exam/change")
    @ResponseBody
    public String change02( MultipartFile context,  String title, String releaseTime,String releaseTime2, String endTime,String endTime2, String stuCode, String teaCode, int paperId, HttpServletRequest request) throws IOException, ParseException {
        String msg=null;

        //文件
        byte[] context0=context.getBytes();

        String startTime = transformTime(releaseTime,releaseTime2);
        String overTime = transformTime(endTime,endTime2);
        Exampapers exampapers=new Exampapers(paperId, startTime, overTime, context0,  stuCode,  teaCode, title);
         msg=paperService.changePaper(exampapers);
        return msg;
    }
    //删除
    @RequestMapping("/exam/delete/{id}")
    public String delete(@PathVariable("id") int id){
        paperService.deletePaper(id);
        return "forward:/find";
    }
}
