package com.controller;

import com.pojo.Exampapers;
import com.pojo.Monitor;
import com.pojo.Teacher;
import com.service.MonitorService;
import com.service.PaperService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MonitorController {
    @Autowired
    private MonitorService monitorService;
    @Autowired
    @Qualifier("exam")
    private PaperService paperService;

    //查看已经考完的科目列表
    @GetMapping("/monitor/endExamList")
    public String endExamList(HttpServletRequest request,Model model){
        Teacher teacher= (Teacher) request.getSession().getAttribute("user");
        int teacherId=teacher.getId();
        List<Exampapers> exampapersList=paperService.queryEndPaper(teacherId);
        String teacherName=teacher.getName();
        model.addAttribute("exampapers",exampapersList);
        model.addAttribute("teacherName",teacherName);
        return  "/monitor/endExamList";
    }
    //查出考生的监控状态
    @GetMapping("/monitor/messageDetail/{paperId}")
    public String monitorList(@PathVariable("paperId") int paperId, Model model){
        System.out.println("paperId:"+paperId);
        List<Monitor> monitorList = monitorService.queryMonitorList(paperId);
        String title=paperService.queryPaperByPaperId(paperId).getTitle();
        model.addAttribute("monitors",monitorList);
        model.addAttribute("title",title);
        model.addAttribute("monitoring",false);
        return "/monitor/monitorMsg";
    }


    //身份验证
    @GetMapping("/monitor/yanzheng01")
    public String yanzheng01(){
        return "/monitor/monitor_yanzheng";
    }

    @PostMapping("/monitor/yanzheng")
    @ResponseBody
    public String yanzheng(String title,String teaCode){
        System.out.println("title=="+title+"teacode:"+teaCode);
        Map map=new HashMap();
        Exampapers exampaper=paperService.yanzheng(title,teaCode);
        String msg;
        boolean flag=false;
        int paperId=0;
        if(exampaper!=null) {
            msg = "身份验证通过！";
            flag=true;
            paperId=exampaper.getPaperId();
        }
        else    msg="科目或者监考码不正确，请核对后验证！";
        map.put("msg",msg);
        map.put("myFlag",flag);
        map.put("paperId",paperId);
        JSONObject jsonObject=JSONObject.fromObject(map);
        return String.valueOf(jsonObject);
    }

    //实时监控
    @RequestMapping("/monitorList/{paperId}")
    public String monitoring(@PathVariable("paperId") int paperId,Model model){
        List<Monitor> monitorList = monitorService.queryMonitorList(paperId);
        String title=paperService.queryPaperByPaperId(paperId).getTitle();
        model.addAttribute("monitors",monitorList);
        model.addAttribute("title",title);
        model.addAttribute("monitoring",true);
        return "/monitor/monitorMsg";
    }

}
