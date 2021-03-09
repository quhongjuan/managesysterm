package com.service;

import com.mapper.ExamPaperMapper;
import com.mapper.TeacherMapper;
import com.pojo.Exampapers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("exam")
public class PaperService {
    @Autowired
    private ExamPaperMapper examPaperMapper;

    //上传试卷
    public int  addPaper(Exampapers exampapers){
        return examPaperMapper.addPaper(exampapers);
    }
    //通过作者id看所有exam
    public List<Exampapers> queryAllEaxmPaper(int authorId){
        return examPaperMapper.queryPaperList(authorId);
    }
    //查看详情
    public Exampapers findDetail(int paperId){
        return examPaperMapper.queryPaperById(paperId);
    }
    //根据paperId查看作者名字
    public String queryNameByPaperId(int paperId){
       //return examPaperMapper.queryNameByPaperId(paperId).getName();
        String str= examPaperMapper.queryNameByPaperId(paperId).getName();
        System.out.println("name="+str);
        return str;
    }
    //根据paperId 获取考卷
    public  Exampapers queryPaperByPaperId(int paperId){
        return examPaperMapper.queryPaperById(paperId);
    }
    //修改试卷
    public String changePaper(Exampapers exampapers){
       if( examPaperMapper.updatePaper(exampapers)>0)
           return "修改成功,请到查看试卷中查看";
       else return "修改失败";
    }

    //delete
    public void deletePaper(int id){
        examPaperMapper.deletePaper(id);
    }
    //查看已经结束的考试
    public List<Exampapers> queryEndPaper(int authorId){
       return examPaperMapper.queryEndPaperList(authorId);
    }

    //验证监考员身份
    public Exampapers yanzheng(String title,String teaCode){
        return examPaperMapper.yanzhengTeaCode(title,teaCode);
    }

}
