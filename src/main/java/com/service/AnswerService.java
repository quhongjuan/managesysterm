package com.service;

import com.mapper.AnswerMapper;
import com.pojo.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("answer")
public class AnswerService {
    @Autowired
    private AnswerMapper answerMapper;

    //测试添加试卷（本是客户端的工作，这里代为模拟）
    public int  addAnswer(Answer answer){
        return answerMapper.addAnswer(answer);
    }
    //查看某试卷的提交答案的列表
    public List<Answer> queryAnswerList(int paperId){
        return  answerMapper.queryAnswerListByPaperId(paperId);
    }
    //根据试卷id查看学生答案
    public Answer queryAnwerByAnswerId(int answerId){
        return answerMapper.queryAnswerById(answerId);
    }
    //添加分数
    public void addScore(int answerId,String score,String scoreDescribe){
        answerMapper.updateScore(score,scoreDescribe,answerId);
    }
    //更新监控描述
    public void updateMonitorDescribe(String monitorMsg ,int answerId){
        answerMapper.updateMonitorMsg(monitorMsg,answerId);
    }
    //根据studentId 和paperId 获得answerId
    public int queryAnswer(int paperId,int studentId){
       return answerMapper.queryAnswerId(paperId,studentId).getAnswerId();
    }
}
