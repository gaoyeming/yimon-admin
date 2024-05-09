package com.yimon.admin.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: ym.gao
 * @description: 日期转换工具类
 * @date: 2024/4/28 19:22
 */
public class DateFormatUtils {

    private DateFormatUtils() {
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
