package com.daoshun.common;

import java.util.Comparator;

import com.daoshun.guangda.pojo.CscheduleInfo;


public class CscheduleCompare implements Comparator<CscheduleInfo>{
	@Override
	public int compare(CscheduleInfo o1, CscheduleInfo o2) {
		 int i = 0;
         i = o1.getDate().compareTo(o2.getDate()); // 使用字符串的比较
         if(i == 0) { // 如果日期一样,比较时间点,返回比较年龄结果
              return CommonUtils.parseInt(o1.getHour(), 0)-CommonUtils.parseInt(o2.getHour(), 0);
         } else {
              return i; // 名字不一样, 返回比较名字的结果.
         }
	}

}
