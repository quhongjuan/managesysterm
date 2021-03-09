package com;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class ManagesystermApplicationTests {

	@Test
	void contextLoads() {

		for(int i=0;i<10;i++){
			String filename= RandomStringUtils.randomAlphanumeric(10);
			System.out.println(filename);
		}
	}

	@Test
	public void Times(){

		Calendar calendar = Calendar.getInstance();
		Date date=new Date();
		calendar.setTime(date);					//放入Date类型数据
		System.out.println("时间："+date);
		int year =  calendar.get(Calendar.YEAR);					//获取年份
		int month =  calendar.get(Calendar.MONTH);					//获取月份
		int day = calendar.get(Calendar.DATE);					//获取日

		calendar.get(Calendar.HOUR);					//时（12小时制）
		int hour= calendar.get(Calendar.HOUR_OF_DAY);				//时（24小时制）
		int min = calendar.get(Calendar.MINUTE);					//分
		int second = calendar.get(Calendar.SECOND);
		System.out.println(year+"**"+month+"**"+day+"**"+hour+"**"+min+"**"+second);
	}

}
