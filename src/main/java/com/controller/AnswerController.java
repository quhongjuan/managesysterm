package com.controller;

import com.pojo.Answer;
import com.pojo.Exampapers;
import com.pojo.Teacher;
import com.service.AnswerService;
import com.service.PaperService;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.utils.imageToPdf.fileToMultipartFile;

@Controller
public class AnswerController {

    @Autowired
    @Qualifier("answer")
    private AnswerService answerService;
    @Autowired
    @Qualifier("exam")
    private PaperService paperService;

    @GetMapping("/test/add")
    public String test(){
        return "/answer/add";
    }


    @PostMapping("/answer/add")
    @ResponseBody
    public String upload( MultipartFile[] files,int paperId,int studentId,String studentName) throws Exception {
        File f;
        byte[] context;
        MultipartFile multipartFile = null;
        //执行合并+转成MultipartFile
       /* if(toPdf(files)){
            f=new File(path+ "public/imgPdf/hebing.pdf");
            multipartFile= fileToMultipartFile(f);
        }*/
        f=new File("D:\\test\\hebing3.pdf");
        System.out.println("path"+f.getPath());
        multipartFile= fileToMultipartFile(f);
        context= multipartFile.getBytes();
        Answer answer=new Answer(paperId,studentId,studentName,context);
        answerService.addAnswer(answer);
        return "ok";
    }

    //查看已经提交答案的试卷列表
    @RequestMapping("/answer/paperList")
    public String queryAnswerEndPaperList(HttpServletRequest request, Model model){
        Teacher teacher= (Teacher) request.getSession().getAttribute("user");
        int teacherId=teacher.getId();
        List<Exampapers> list=paperService.queryEndPaper(teacherId);
        model.addAttribute("list",list);
        return  "/answer/paperList";
    }
    //查看某科目的提交答案列表

    @GetMapping("/answer/answerList")
    public String queryAnswerList( int paperId ,String title , Model model){
        List<Answer> answersList = answerService.queryAnswerList(paperId);
        model.addAttribute("list",answersList);
        model.addAttribute("title",title);
        return "/answer/answerList";
    }

    //评分阅卷
    @GetMapping("/answer/detail")
    public String readAnswer(int answerId,String title ,Model model){
        Answer answer=answerService.queryAnwerByAnswerId(answerId);
        model.addAttribute("answer",answer);
        model.addAttribute("title",title);
        return "/answer/detail_answer";
    }
    //在线预览答案
    @RequestMapping("/pdf2")
    public void queryPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        Optional<Answer> answer= Optional.ofNullable(answerService.queryAnwerByAnswerId(id));
        if(answer.isPresent()){
            byte[] fileBytes=answer.get().getAnswerContext();
            response.getOutputStream().write(fileBytes);
        }
    }
    //提交分数
    @PostMapping("/answer/score")
    @ResponseBody
    public String submitScore(int answerId,String score,String scoreDescribe){
        System.out.println("answerId="+answerId+"  score="+ score+"  scoreDescribe= "+ scoreDescribe);
        answerService.addScore(answerId,score,scoreDescribe);
        return "成绩提交成功，请到成绩列表查看！";
    }
    //只读答案
    @GetMapping("/answer/detailOnlyRead")
    public String detailOnlyRead(int answerId,String title ,Model model){
        Answer answer=answerService.queryAnwerByAnswerId(answerId);
        model.addAttribute("answer",answer);
        model.addAttribute("title",title);
        return "/answer/detail_answer_onlyread";
    }

}
