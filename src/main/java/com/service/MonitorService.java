package com.service;

import com.mapper.MonitorMapper;
import com.pojo.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorService {
    @Autowired
    private MonitorMapper monitorMapper;
    @Autowired
    @Qualifier("answer")
    private AnswerService answerService;

    static int qieping = 3;
    static int xiping = 5;
    static int likai = 1;
    static int duoren = 2;

    //根据科目id查到学生监控列表
    public List<Monitor> queryMonitorList(int paperId){
        List<Monitor> monitors = new ArrayList<Monitor>();
        monitors = monitorMapper.queryAllByPaperId(paperId);
        String str_status=null;
        String str_describe=null;
        Monitor monitor=null;

        for (int i=0;i<monitors.size();i++){
            monitor =monitors.get(i);
            //更新状态
            if((monitor.getQieping()>qieping)||(monitor.getXiping()>xiping)
                    ||(monitor.getLikai()>likai)||(monitor.getDuoren()>duoren)){
                str_status="状态:异常（"+"切屏"+monitor.getQieping()+"次/息屏"+monitor.getXiping()+"次/离开座位"+monitor.getLikai()+"次/环境出现无关人员"+monitor.getDuoren()+"次)";
            }else{
                str_status="状态：正常";
            }
            monitorMapper.updateStatus(monitor.getMonitorId(),str_status);
            //拼接描述
            str_describe=monitor.getStudentName()+"同学监控状态描述：切屏"+monitor.getQieping()+"次/息屏"+monitor.getXiping()+"次/离开座位"+monitor.getLikai()+"次/环境出现无关人员"+monitor.getDuoren()+"次";
            monitorMapper.updateDescribe(monitor.getMonitorId(),str_describe);
            //这里顺便更新一下学生答案中的监控描述
            //这里学生可能还没有提交答案得做一下修正
            answerService.updateMonitorDescribe(str_describe,answerService.queryAnswer(monitor.getPaperId(),monitor.getStudentId()));
        }

        return monitorMapper.queryAllByPaperId(paperId);
    }

    //
}
