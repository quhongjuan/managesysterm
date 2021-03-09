package com.mapper;

import com.pojo.Monitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MonitorMapper {
    Monitor queryByStudentId(int studentId);
    List<Monitor> queryAllByPaperId(int paperId);
    int updateStatus(@Param("monitorId") int monitorId ,@Param("status") String status);
    int updateDescribe(@Param("monitorId") int monitorId ,@Param("describe") String describe);
}
