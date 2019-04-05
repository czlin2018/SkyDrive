
package com.comment.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: czl
 * @Date: 2018 05 02 上午9:19
 * @Desc:
 */
public class DateApi{


    static Logger log = LoggerFactory.getLogger(DateApi.class);


    /**
     * 用来当成随机码
     */
    public static String getTimeId (){
        long timesId = System.currentTimeMillis();
        String str = String.valueOf(timesId);
        str = str.substring(str.length() - 6, str.length() - 1);
        return str;
    }

    public static String getPicTimeId (){
        long timesId = System.currentTimeMillis();
        String str = "Pic" + String.valueOf(timesId);
        return str;
    }

    /**
     * 获取现在时间
     */
    public static Date currentDateTime (){
        return new Date();
    }

    /**
     * 获取当前年
     */
    public static Integer getYear (Date date){
        if(date == null){
            throw new IllegalArgumentException("The date must not be null");
        } else{
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.YEAR);
        }
    }

    /**
     * 获取当前月
     */
    public static Integer getMonth (Date date){
        if(date == null){
            throw new IllegalArgumentException("The date must not be null");
        } else{
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.MONTH) + 1;
        }
    }

    /**
     * 获得当天的开始时间
     */
    public static Date getCurrentDayStartTime (){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获得当天的结束时间
     */
    public static Date getCurrentDayEndTime (){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }


    /**
     * 获得本月的开始时间
     */
    public static Date getCurrentMonthStartTime (){
        Calendar c = Calendar.getInstance();
        Date now = null;
        try{
            c.set(Calendar.DATE, 1);
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            now = SHORT_SDF.parse(SHORT_SDF.format(c.getTime()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取本月的结束时间
     */
    public static Date getCurrentMonthEndTime (){
        Calendar c = Calendar.getInstance();
        Date now = null;
        try{
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, - 1);
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            now = LONG_SDF.parse(SHORT_SDF.format(c.getTime()) + " 23:59:59");
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取指定年月的起点时间
     *
     * @param day 不传默认指定月第一天
     */
    public static Date getAppointStartTime (Integer year, Integer month, Integer day){
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate;
        if(day != null){
            localDate = yearMonth.atDay(day);
        } else{
            localDate = yearMonth.atDay(1);
        }
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获取指定年月日的终点时间
     *
     * @param day 　　不传默认指定月最后一天
     */
    public static Date getAppointEndTime (Integer year, Integer month, Integer day){
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate dateTime;
        if(day != null){
            dateTime = yearMonth.atDay(day);
        } else{
            dateTime = yearMonth.atEndOfMonth();
        }
        LocalDateTime localDateTime = dateTime.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 获取当前年的开始时间
     */
    public static Date getCurrentYearStartTime (){
        Calendar c = Calendar.getInstance();
        Date now = null;
        try{
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            now = SHORT_SDF.parse(SHORT_SDF.format(c.getTime()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取当前年的结束时间
     */
    public static Date getCurrentYearEndTime (){
        Calendar c = Calendar.getInstance();
        Date now = null;
        try{
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            now = LONG_SDF.parse(SHORT_SDF.format(c.getTime()) + " 23:59:59");
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取当前季度的开始时间
     */
    public static Date getCurrentQuarterStartTime (){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try{
            if(currentMonth >= 1 && currentMonth <= 3){
                c.set(Calendar.MONTH, 0);

            } else if(currentMonth >= 4 && currentMonth <= 6){
                c.set(Calendar.MONTH, 3);

            } else if(currentMonth >= 7 && currentMonth <= 9){
                c.set(Calendar.MONTH, 4);

            } else if(currentMonth >= 10 && currentMonth <= 12){
                c.set(Calendar.MONTH, 9);
            }
            c.set(Calendar.DATE, 1);
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            now = LONG_SDF.parse(SHORT_SDF.format(c.getTime()) + " 00:00:00");
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取当前季度的结束时间
     */
    public static Date getCurrentQuarterEndTime (){
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try{
            if(currentMonth >= 1 && currentMonth <= 3){
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if(currentMonth >= 4 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if(currentMonth >= 7 && currentMonth <= 9){
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if(currentMonth >= 10 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            SimpleDateFormat SHORT_SDF = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            now = LONG_SDF.parse(SHORT_SDF.format(c.getTime()) + " 23:59:59");
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }


    /**
     * 获取当前季度的结束时间
     */
    public static Date formatLongSdfStrToDate (String dateStr){
        try{
            SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = LONG_SDF.parse(dateStr);
            return date;
        }catch(Exception e){
            log.error("error occur when formatLongSdfStrToDate,for dateStr:{}", dateStr, e);
            return null;
        }
    }

    public static String formatLongSdfToDate (Date target){
        if(null == target){
            return "";
        }
        SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return LONG_SDF.format(target);
    }

}