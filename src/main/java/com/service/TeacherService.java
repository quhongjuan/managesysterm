package com.service;

import com.mapper.TeacherMapper;
import com.pojo.Teacher;
import com.utils.createString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service("teacher")
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    public JavaMailSenderImpl mailSender;
    /**
     * 添加教师
     */
    // if(1)==>ok; if(0)==>学号已存在
    public boolean addTeacher(Teacher t){
        if(teacherMapper.queryUserByNum(t.getNumber())==null) {
            if (teacherMapper.addUser(t) != 0) {
                System.out.println("添加成功！");
                return true;
            }
            else return false;
        }
        else return false;
    }
    /**
     * 登录验证
     */
    public Teacher yanzheng(String number,String password){
        Teacher stu=teacherMapper.queryUserByNum(number);
        if(stu!=null) {
            if (stu.getPassword().equals(password)) {
                System.out.println("验证身份成功：" + stu);
                return stu;
            }
        }
        return null;
    }

    /**
     * 通过学工号查找老师
     */
    public Teacher queryTeacherByNumber(String number){
        return  teacherMapper.queryUserByNum(number);
    }
    /**
     * 修改教师信息
     */
    public boolean updateTeacherInfo(Teacher stu){
        if(teacherMapper.updateInfo(stu)>0)
            return true;
        else    return false;
    }
    /**
     * 修改教师密码
     */

    public String  updateTeacherPwd(String number,String OldPassword,String NewPassword){
        if(teacherMapper.queryUserByNumAndPasswd(number,OldPassword)!=null){
            if(teacherMapper.updatePwd(number,NewPassword)>0)
                return "修改成功";
        }
        return "学工号不存在或者密码错误";
    }

    //找回密码
    public String sendEmail(String number,String email){
        Teacher teacher=teacherMapper.queryUserByNum(number);
        String msg=null;
        if(teacher!=null){
            if(teacher.getEmail().equals(email)){
               String newPasswd = send(email);
               teacherMapper.updatePwd(number,newPasswd);
                System.out.println("已发邮件");
               msg="重置密码已经发送到您的邮箱，请注意查收！登录后请修改新密码";
            }
            else msg="邮箱不正确！";
        }else msg="学工号不存在！";
        return msg;
    }

    public String send(String email){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("重置密码");
        mailMessage.setTo(email);
        mailMessage.setFrom("3092603759@qq.com");
        String newPasswd=createString.createRedonString();
        String str="请重新登录，重置密码为："+ newPasswd;
        mailMessage.setText(str);
        mailSender.send(mailMessage);
        System.out.println("邮件内容："+str);
        return newPasswd;
    }

    /**
     * 查看所有老师
     */
    public List<Teacher> queryAllTeacher(){
       List<Teacher> teachers = teacherMapper.queryUserList();
        Iterator<Teacher> it = teachers.iterator();
        while(it.hasNext()){
            if(it.next().getNumber().equals("10086")){
                it.remove();
            }
        }
        return teachers;
    }

    /**
     * 删除老师
     */
    public void deleteTeacher(int teacherId){
        teacherMapper.deleteTeacher(teacherId);
    }

    /**
     * 通过id 查看老师
     */
    public Teacher queryTeacherById(int teacherId){
        return teacherMapper.queryTeacherById(teacherId);
    }
}
