package org.linlinjava.litemall.db.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description 获取时间日期工具类
 * @Author lanye
 * @Date 2018/5/9 10:58
 **/
public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String args[]){
        System.out.println(getCurrentMonday(dateFormat));
        System.out.println(getPreviousSunday(dateFormat));
        System.out.println("start"+getDayStartString());
        System.out.println("end"+getDayEndString());
        System.out.println(getMinMonthDate("2017-04-17"));
        System.out.println(getMaxMonthDate("2017-04-17"));
    }
    // 获得本周一与当前日期相差的天数
    /**
      * @author lanye
      * @Description 获得本周一与当前日期相差的天数
      * @Date 2018/5/9 11:17
      * @Param []
      * @return int
      **/
    public static  int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
      * @author lanye
      * @Description 获得当前周- 周一的日期
      * @Date 2018/5/9 11:30
      * @Param [simpleDateFormat]
      * @return java.lang.String
      **/
    public static  String getCurrentMonday(SimpleDateFormat simpleDateFormat) {
        Date monday = getCurrentMondayDate();
        DateFormat df = DateFormat.getDateInstance();
        if(simpleDateFormat != null){
            return simpleDateFormat.format(monday);
        }
        return df.format(monday);
    }

    /**
      * @author lanye
      * @Description 获得当前周周一的开始时间
      * @Date 2018/5/9 11:27
      * @Param []
      * @return java.util.Date
      **/
    public static Date getCurrentMondayDate() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        currentDate.set(Calendar.HOUR_OF_DAY,0);
        currentDate.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Date monday = currentDate.getTime();
        return monday;
    }

    /**
      * @author lanye
      * @Description 获得当天的开始时间
      * @Date 2018/5/16 14:33
      * @Param []
      * @return java.lang.String
      **/
    public static String getDayStartString() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        String dayStart = dateFormat2.format(todayStart.getTime());
        return dayStart;
    }

    /**
      * @author lanye
      * @Description 获得当天的结束时间
      * @Date 2018/5/16 14:33
      * @Param []
      * @return java.lang.String
      **/
    public static String getDayEndString() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        String dayEnd = dateFormat2.format(todayEnd.getTime());
        return dayEnd;
    }

    /**
      * @author lanye
      * @Description 获得当前周- 周日的日期
      * @Date 2018/5/9 11:17
      * @Param []
      * @return java.lang.String
      **/
    public static String getPreviousSunday(SimpleDateFormat simpleDateFormat) {
        Date monday = getPreviousSundayDate();
        DateFormat df = DateFormat.getDateInstance();
        if(simpleDateFormat != null){
            return simpleDateFormat.format(monday);
        }
        return df.format(monday);
    }

    /**
      * @author lanye
      * @Description 获得当前周周日的截止时间
      * @Date 2018/5/9 11:31
      * @Param []
      * @return java.util.Date
      **/
    public static Date getPreviousSundayDate() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.MILLISECOND, 999);
        Date monday = currentDate.getTime();
        return monday;
    }

    /**
      * @author lanye
      * @Description 获得当前月--开始日期
      * @Date 2018/5/9 11:17
      * @Param [date]
      * @return java.lang.String
      **/
    public static String getMinMonthDate(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            return dateFormat.format(calendar.getTime());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
      * @author lanye
      * @Description 获得当前月--结束日期
      * @Date 2018/5/9 11:18
      * @Param [date]
      * @return java.lang.String
      **/
    public static String getMaxMonthDate(String date){
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return dateFormat.format(calendar.getTime());
        }  catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
