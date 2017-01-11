// Copyright 2014 www.refanqie.com Inc. All Rights Reserved.

package com.dreambox.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class DateUtils {
    public static final String YYYYMMDDHHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDDHHMMSSMS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    private static final String YYYYMMDD_FORMAT = "yyyy-MM-dd";
    private static final String YYYYMMINTFORMAT = "yyyyMM";
    public static final String YYYYMMDDINTFORMAT = "yyyyMMdd";
    private static final String YYYYMMDDTHHMMSSZ_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String MMDDYYYY_FORMAT = "MM/dd/yyyy";
    public static final String YYYYMMDDHHMM_FORMAT = "yyyyMMdd_HHmm";

    /**
     * 返回今日日期，时间全为0，即0点时候的日期
     * 
     * @return
     */
    public static Date getTodayDate() {
        return formatToDayStart(new Date());
    }

    public static Date formatToDayStart(Date day) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        return c.getTime();
    }

    public static Date parseMdyFormat(String date) throws ParseException {
        return new SimpleDateFormat(MMDDYYYY_FORMAT).parse(date.trim());
    }

    public static String getToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        return new SimpleDateFormat(YYYYMMDDHHMMSS_FORMAT).format(c.getTime());
    }

    public static String getTodayYmd() {
        Calendar c = Calendar.getInstance();
        return new SimpleDateFormat(YYYYMMDD_FORMAT).format(c.getTime());
    }

    public static String getNow() {
        return new SimpleDateFormat(YYYYMMDDHHMMSS_FORMAT).format(new Date());
    }

    public static String getDeltaNow(int deltaDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, deltaDays);
        return new SimpleDateFormat(YYYYMMDDHHMMSS_FORMAT).format(c.getTime());
    }

    public static Date parseStr(String timeString) {
        return parseDateStr(timeString, new Date());
    }

    public static boolean beforeNow(String timeString) {
        if (StringUtils.isEmpty(timeString)) {
            return false;
        }

        Date sdate = parseDateStr(timeString);
        return beforeNow(sdate);
    }

    public static boolean beforeNow(Date date) {
        return date.before(new Date());
    }

    public static boolean before(String time, String time1) throws IllegalArgumentException {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(time1)) {
            return true;
        }
        Date sdate = parseDateStr(time);
        Date edate = parseDateStr(time1);
        return sdate.before(edate);
    }

    /**
     * NEVER RETURN NULL
     * 
     * @param timeString
     * @return
     * @throws IllegalArgumentException
     */
    public static Date parseDateStr(String timeString) throws IllegalArgumentException {
        return parseDateStr(timeString, null);
    }

    public static Date parseMsDateStr(String timeString) throws IllegalArgumentException {
        return parseDateStr(timeString, YYYYMMDDHHMMSSMS_FORMAT, null);
    }


    public static Date parseDateStr(String timeString, Date defaultDate) throws IllegalArgumentException {
        if (StringUtils.isEmpty(timeString)) {
            if (defaultDate == null) {
                throw new IllegalArgumentException("无效的格式化时间串,timeFormatString:" + timeString);
            }
            return defaultDate;
        }

        if (timeString.length() > 10) {
            return parseLongStr(timeString, defaultDate);
        } else {
            if (timeString.length() == 8) {
                return parseYMDStr(timeString, defaultDate);
            }
            return parseShortStr(timeString, defaultDate);
        }
    }

    public static Date parseLongStr(String timeString, Date defaultDate) {
        return parseDateStr(timeString, YYYYMMDDHHMMSS_FORMAT, defaultDate);
    }

    public static Date parseTZLongStr(String timeString, Date defaultDate) {
        return parseDateStr(timeString, YYYYMMDDTHHMMSSZ_FORMAT, defaultDate);
    }

    public static Date parseLongStr(String timeString) throws ParseException {
        return parseLongStr(timeString, null);
    }

    public static Date parseShortStr(String timeString) {
        return parseShortStr(timeString, null);
    }

    public static Date parseShortStr(String timeString, Date defaultDate) {
        return parseDateStr(timeString, YYYYMMDD_FORMAT, defaultDate);
    }

    public static Date parseYMDStr(String timeString, Date defaultDate) {
        return parseDateStr(timeString, YYYYMMDDINTFORMAT, defaultDate);
    }


    public static Date parseDateStr(String timeString, String timeFormat, Date defaultDate)
            throws IllegalArgumentException {
        if (StringUtils.isEmpty(timeString)) {
            if (defaultDate == null) {
                throw new IllegalArgumentException("无效的格式化时间串" + timeFormat + ",timeFormatString:" + timeString);
            }
            return defaultDate;
        }
        try {
            return new SimpleDateFormat(timeFormat).parse(timeString);
        } catch (ParseException e) {
            if (defaultDate == null) {
                throw new IllegalArgumentException("无效的格式化时间串" + timeFormat + ",timeFormatString:" + timeString);
            }
            return defaultDate;
        }
    }

    public static int currentYearMonth() {
        Calendar c = Calendar.getInstance();
        return formatDateIntValue(c.getTime(), YYYYMMINTFORMAT);
    }

    public static String getYearMonthStr(Date parseDate) {
        return formatStr(parseDate, YYYYMMINTFORMAT);
    }

    public static String formatStr(Date date, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(date).toString();
    }

    public static Date parseStr(String src, String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).parse(src);
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效的格式化时间串" + src + ",dateFormat:" + dateFormat);

        }
    }

    public static int currentYearMonthDay() {
        Calendar c = Calendar.getInstance();
        return formatDateIntValue(c.getTime(), YYYYMMDDINTFORMAT);
    }

    public static int preMonth(int dbMonth) {
        return deltaMonth(dbMonth, -1);
    }

    public static int deltaMonth(int dbMonth, int delta) {
        try {
            Date date = new SimpleDateFormat(YYYYMMINTFORMAT).parse(String.valueOf(dbMonth));
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, delta);
            return Integer.parseInt(new SimpleDateFormat(YYYYMMINTFORMAT).format(c.getTime()).toString());
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效的格式化时间串" + YYYYMMINTFORMAT + ",timeFormatString:" + dbMonth);
        }
    }

    public static int getDateYMDIntValue(Date updateTime) {
        return formatDateIntValue(updateTime, YYYYMMDDINTFORMAT);
    }

    public static String getDateYMDStringValue(Date updateTime) {
        return String.valueOf(getDateYMDIntValue(updateTime));
    }

    private static int formatDateIntValue(Date updateTime, String dateIntFormat) {
        return Integer.parseInt(formatStr(updateTime, dateIntFormat));
    }

    public static Date addDays(Date statDate, int delta) {
        return org.apache.commons.lang3.time.DateUtils.addDays(statDate, delta);
    }


    public static Date addMonths(Date statDate, int delta) {
        return org.apache.commons.lang3.time.DateUtils.addMonths(statDate, delta);
    }

    public static boolean isYesterDay(Date value, Date currentDate) {
        return isSameDate(addDays(value, 1), currentDate);
    }

    public static boolean isZeroHour(Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        return c.get(Calendar.HOUR_OF_DAY) == 0;
    }

    public static boolean isSameDate(Date pathDate, Date now) {
        Calendar c = Calendar.getInstance();
        c.setTime(pathDate);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(now);
        return c.get(Calendar.DAY_OF_YEAR) == nowCalendar.get(Calendar.DAY_OF_YEAR)
                && c.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR);
    }

    public static Date getTomorrow() {
        return addDays(new Date(), 1);
    }

}
