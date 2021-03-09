package com.mapper;

import com.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnswerMapper {
    int addAnswer(Answer answer);
    Answer queryAnswerById(int answerId);
    List<Answer> queryAnswerListByPaperId(int paperId);
    int updateScore(@Param("score") String score,@Param("scoreDescribe")String scoreDescribe,@Param("answerId") int answerId);
    int updateMonitorMsg(@Param("monitorMsg") String monitorMsg,@Param("answerId") int answerId);
    //获取answerId
    Answer queryAnswerId(@Param("paperId") int paperId,@Param("studentId") int studentId);
}
