package com.utils;

import com.pojo.Teacher;

public class admin {
    /**
     * 管理员账号
     * 学工号：10086
     * 密码：123456
     */

    public static boolean isAdmin(Teacher teacher){
        if(teacher.getNumber().equals("10086")&& teacher.getPassword().equals("123456")) return true;
        else return  false;
    }
}
