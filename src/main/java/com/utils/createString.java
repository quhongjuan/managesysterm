package com.utils;

import com.mapper.TeacherMapper;
import com.service.PaperService;
import com.service.TeacherService;
import org.apache.commons.lang.RandomStringUtils;

public class createString {
    public static String createRedonString(){
        String str= RandomStringUtils.randomAlphanumeric(10);
        return str;
    }

}
