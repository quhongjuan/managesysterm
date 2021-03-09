package com.mapper;

import com.pojo.Exampapers;
import com.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamPaperMapper {
    List<Exampapers> queryPaperList(int authorId);
    List<Exampapers> queryEndPaperList(int authorId);
    Exampapers queryPaperById(int paperId);
    Teacher queryNameByPaperId(int paperId);
    int addPaper(Exampapers paper);
    int updatePaper(Exampapers paper);
    int deletePaper(int paperId);
    Exampapers yanzhengTeaCode(@Param("title") String title,@Param("teaCode") String teaCode);
}
