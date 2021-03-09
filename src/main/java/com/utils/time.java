package com.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class time {
    //将12 小时制时间转换成24小时制
    public static String transformTime(String nyr,String oldTime) throws ParseException {
        DateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        DateFormat dateFormat24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String abc= "2008-07-10 07:20:00 下午";
        String a=null;
        if(oldTime.contains("P")){
            a=oldTime.replace("PM","下午");
        }else  a=oldTime.replace("AM","上午");

        String abc=nyr+" "+a;

        Date date = null;
        date = dateFormat12.parse(abc);
        abc= dateFormat24.format(date);
        return abc;
    }
}
