package com.mapper;



import com.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {
    List<Teacher> queryUserList();
    Teacher queryUserByNum(String number);
    Teacher queryTeacherById(int id);
    int addUser(Teacher student);
    int updateInfo(Teacher student);
    Teacher queryUserByNumAndPasswd(@Param("number") String number, @Param("password") String password);
    int updatePwd(@Param("number") String number,@Param("newPwd") String newPwd );
    int deleteTeacher(int teacherId);
}
