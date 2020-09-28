package com.lzf.greendao.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class DataUtils {

    public static String getAMonthAgoData(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -30);
        String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
        return endDate;
    }
}
