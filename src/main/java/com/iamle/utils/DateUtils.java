package com.iamle.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATEKEY_PATTERN = "yyyyMMdd";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String HOUR_PATTERN = "HH";
    public static final String MINUTE_PATTERN = "mm";

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DateUtils.DATE_PATTERN);
    public static final SimpleDateFormat DATEKEY_FORMAT = new SimpleDateFormat(DateUtils.DATEKEY_PATTERN);


    /**
     * 时间格式化成字符串
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date date, String pattern) throws ParseException {
        if (ObjectUtils.isEmpty(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将日期时间格式转化为日期格式字符串
     *
     * @param datetime
     * @return
     * @throws ParseException
     */
    public static String dateFormat(Date datetime) throws ParseException{
        return DateUtils.dateFormat(datetime,DateUtils.DATE_PATTERN);
    }

    /**
     * 字符串解析成时间对象
     *
     * @param dateTimeString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date dateParse(String dateTimeString, String pattern) throws ParseException {
        if (ObjectUtils.isEmpty(pattern)) {
            pattern = DateUtils.DATE_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateTimeString);
    }

    /**
     * 时间戳毫秒值转化为字符串
     * @return
     */
    public static String fromUnixtime(Long timestamp,String pattern){

        String dateStr = null;
        try{
            if(String.valueOf(timestamp).length() == 13) {
                dateStr = dateFormat(new Date(timestamp), pattern);
            }else if(String.valueOf(timestamp).length() == 10){
                dateStr = dateFormat(new Date(timestamp*1000), pattern);
            }
            return dateStr;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 时间比较（如果myDate > compareDate返回1，myDate < compareDate 返回-1，myDate == compareDate返回0）
     *
     * @param myDate
     * @param compareDate
     * @return
     */
    public static int dateCompare(Date myDate, Date compareDate) {
        Calendar myCal = Calendar.getInstance();
        Calendar compareCal = Calendar.getInstance();
        myCal.setTime(myDate);
        compareCal.setTime(compareDate);
        return myCal.compareTo(compareCal);
    }

    /**
     * 判断一个时间是否在另一个时间之前
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean before(String time1, String time2) throws ParseException{
        Date dateTime1 = DATE_TIME_FORMAT.parse(time1);
        Date dateTime2 = DATE_TIME_FORMAT.parse(time2);

        if (dateTime1.before(dateTime2)) {
            return true;
        }

        return false;
    }

    /**
     * 判断一个时间是否在另一个时间之后
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean after(String time1, String time2) throws ParseException {
        Date dateTime1 = DATE_TIME_FORMAT.parse(time1);
        Date dateTime2 = DATE_TIME_FORMAT.parse(time2);

        if (dateTime1.after(dateTime2)) {
            return true;
        }

        return false;
    }

    /**
     * 获取两个时间中最大的一个时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date dateMax(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }

        if (1 == dateCompare(date1, date2)) {
            return date1;
        } else if (-1 == dateCompare(date1, date2)) {
            return date2;
        }
        return date1;
    }

    /**
     * 获取两个时间中最小的一个时间
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date dateMin(Date date1, Date date2) {
        if (date1 == null) {
            return date2;
        }
        if (date2 == null) {
            return date1;
        }
        if (1 == dateCompare(date1, date2)) {
            return date2;
        } else if (-1 == dateCompare(date1, date2)) {
            return date1;
        }
        return date1;
    }

    /**
     * 获得格式化后的当前日期时间：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDatetime(){
        return DATE_TIME_FORMAT.format(new Date());
    }

    /**
     * 获得格式化后的当前日期：yyyy-MM-dd
     * @return
     */
    public static String getCurrentDate(){
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获得日期字符串:yyyyMMdd
     * @return
     */
    public static String getDateKey(){
        return DateUtils.DATEKEY_FORMAT.format(new Date());
    }

    /**
     * 获得日期字符串:yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String getDateKey(Date date){
        return DateUtils.DATEKEY_FORMAT.format(date);
    }

    /**
     * 格式化日期key
     *
     * @param datekey
     * @return
     */
    public static Date parseDateKey(String datekey) {
        try {
            return DATEKEY_FORMAT.parse(datekey);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期key
     *
     * @param date
     * @return
     */
    public static String formatDateKey(Date date) {
        return DATEKEY_FORMAT.format(date);
    }


    /**
     * 获取日期时间的年份：yyyy
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份:MM
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期:dd
     *
     * @param date
     * @return
     */
    public static int getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取日期时间当年的总天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 日期加减
     *
     * @param date
     * @param interval
     * @param includeTime
     * @return
     */
    public static Date dateAdd(Date date,Integer interval,boolean includeTime) throws ParseException {
        if (date == null) {
            date = new Date();
        }

        if (!includeTime) {
            date = DateUtils.DATE_FORMAT.parse(DateUtils.DATE_FORMAT.format(date));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, interval);
        return cal.getTime();
    }


    /**
     * 以key方式获取昨天日期
     *
     * @return
     */
    public static String getYestodayKey(){

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        return DateUtils.DATEKEY_FORMAT.format(cal.getTime());

    }


    /**
     * 时间秒级别加减
     *
     * @param date
     * @return
     */
    public static String timeAddOnSecond(Date date,int amount){

        Calendar cal = Calendar.getInstance();
        if(date == null){
            cal.setTime(new Date());
        }else{
            cal.setTime(date);
        }
        cal.add(Calendar.SECOND, amount);

        return DateUtils.DATE_TIME_FORMAT.format(cal.getTime());

    }

    /**
     * 将输入日期字符串转换为 时间戳
     *
     * @param dateString
     * @param pattern
     * @return
     */
    public static Long getTimestamp(String dateString ,String pattern){

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }

    }

}
