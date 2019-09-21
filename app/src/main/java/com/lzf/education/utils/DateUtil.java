package com.lzf.education.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/21.
 */
public class DateUtil {

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }
}
