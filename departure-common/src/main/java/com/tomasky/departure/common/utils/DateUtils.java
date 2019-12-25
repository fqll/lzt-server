package com.tomasky.departure.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by sam on 2019-08-08.11:15
 */
public class DateUtils {

    public static final String FORMAT_DATE_STR = "yyyy-MM-dd";
    public static final String FORMAT_MONTH_STR = "yyyy-MM";
    public static final String FORMAT_DATE_TIME_STR = "MM-dd HH:mm";

    public static void main(String[] args) {
        System.out.println(getLastMonth("2019-12"));
        System.out.println(getLastMonth("2019-10"));
        System.out.println(getLastMonth("2019-01"));
    }

    /**
     * 获取当月第一天
     * @return
     */
    public static String getFirstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format(calendar.getTime());
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static String getFirstDayOfCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return format(calendar.getTime());
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static String getLastDayOfCurrentYear() {
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return format(calendar.getTime());
    }

    public static List<String> getCurrentYearMonthStrList() {
        List<String> currentYearMonthStrList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for(int i = 0; i < 12; i ++) {
            calendar.set(Calendar.MONTH, i);
            currentYearMonthStrList.add(format(calendar.getTime(), FORMAT_MONTH_STR));
        }
        Collections.reverse(currentYearMonthStrList);
        return currentYearMonthStrList;
    }

    /**
     * 获取上月
     * @param yearMonth 格式为yyyy-MM
     * @return
     */
    public static String getLastMonth(String yearMonth) {
        String[] split = yearMonth.split("-");
        String year = split[0];
        String month = split[1];
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        return format(calendar.getTime(), FORMAT_MONTH_STR);
    }

    /**
     * 格式化日期为String型(yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, FORMAT_DATE_STR);
    }

    /**
     * 根据指定日期格式格式化日期为String型
     *
     * @param date
     * @param formater
     * @return
     */
    public static String format(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }
}