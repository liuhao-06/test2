package com.baidu.bpit.git_test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getNowTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
