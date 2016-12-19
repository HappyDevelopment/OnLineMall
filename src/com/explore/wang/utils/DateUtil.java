package com.explore.wang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间类
 * Created by 王兆琦  on 2016/12/19 18.05.
 */
public class DateUtil {
    private static String time;

    public static String getTime() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-sss");

        return simpleDateFormat.format(new Date());

    }
}
