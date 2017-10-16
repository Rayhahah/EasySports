package com.rayhahah.rbase.utils.base;


import com.rayhahah.rbase.utils.useful.RLog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 日期类的时间操作
 *
 * @author Administrator
 */
public class DateTimeUitl {
    public final static String SYS_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

    public final static String TIME_WITH_SECOND_FORMATE = "yyyy-MM-dd HH:mm";

    /**
     * 返回当前时间序列
     *
     * @return
     */
    public static String getTimeSeq() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取当前时间的n天前的日期
     *
     * @param day
     * @return
     */
    public static String getBeforeCurentTimes(int day) {
        String time = null;
        Calendar calendar = calGetCalendarTime();
        calendar.add(Calendar.DATE, -day);
        time = getSysDateTime(calendar.getTime());
        return time;
    }

    /**
     * 是否是系统时间
     *
     * @param time
     * @return
     */
    public static boolean isSysDateTime(String time) {
        boolean flag = false;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_WITH_SECOND_FORMATE);
            formatter.parse(time);
            flag = true;
        } catch (Exception ex) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将yyyy-mm-ddTHH:mm:ss+HH:mm 的时间调整系统格式的时间
     *
     * @param time
     * @return
     */
    public static String gmtTimeToSysFormateTime(String time) {
        String result = null;
        try {
            int pos = time.indexOf("+");
            //String left=pos>0?time.substring(pos, time.length()):"";
            time = pos > 0 ? time.substring(0, pos) : time;
            result = time;
            result = time.replace("T", " ").trim();
            //result=getDateTimeWithOutSenondString(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 获取当前日期格式的下一天
     *
     * @param targetDate 目标日期格式字符串
     * @return
     */
    public static String getFutureFromTarget(String targetDate) {
        int year = DateTimeUitl.intGetYear(targetDate);
        int month = DateTimeUitl.intGetMonth(targetDate);
        int day = DateTimeUitl.intGetDay(targetDate);
        boolean isLeapYear = false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            isLeapYear = true;
        } else {
            isLeapYear = false;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
                if (day == 31) {
                    day = 1;
                    month++;
                } else {
                    day++;
                }
                break;
            case 12:
                if (day == 31) {
                    day = 1;
                    month = 1;
                    year++;
                } else {
                    day++;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day == 30) {
                    day = 1;
                    month++;
                } else {
                    day++;
                }
                break;
            case 2:
                if (isLeapYear) {
                    if (day == 29) {
                        day = 1;
                        month++;
                    } else {
                        day++;
                    }
                } else {
                    if (day == 28) {
                        day = 1;
                        month++;
                    } else {
                        day++;
                    }
                }
                break;
            default:
                break;
        }
        return formatDateFromInt(year, month, day);
    }

    /**
     * 获取当前日期格式的前一天
     *
     * @param targetDate 目标日期格式字符串
     * @return
     */
    public static String getBeforeFromTarget(String targetDate) {
        int year = DateTimeUitl.intGetYear(targetDate);
        int month = DateTimeUitl.intGetMonth(targetDate);
        int day = DateTimeUitl.intGetDay(targetDate);
        boolean isLeapYear = false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            isLeapYear = true;
        } else {
            isLeapYear = false;
        }
        switch (month) {
            case 1:
                if (day == 1) {
                    day = 31;
                    month = 12;
                    year--;
                } else {
                    day--;
                }
                break;
            case 5:
            case 7:
            case 10:
            case 12:
                if (day == 1) {
                    day = 30;
                    month--;
                } else {
                    day--;
                }
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
                if (day == 1) {
                    day = 31;
                    month--;
                } else {
                    day--;
                }
                break;
            case 3:
                if (isLeapYear) {
                    if (day == 1) {
                        day = 29;
                        month--;
                    } else {
                        day--;
                    }
                } else {
                    if (day == 1) {
                        day = 28;
                        month--;
                    } else {
                        day--;
                    }
                }
                break;
            default:
                break;
        }
        return formatDateFromInt(year, month, day);
    }

    /**
     * 将int数组的  年份、月份、日，转换为  2017-06-05 的格式
     *
     * @return
     */
    public static String formatDateFromInt(int year, int month, int day) {
        String m = "";
        if (month >= 10) {
            m = "" + month;
        } else {
            m = "0" + month;
        }

        String d = "";
        if (day >= 10) {
            d = "" + day;
        } else {
            d = "0" + day;
        }
        RLog.e("3!! y=" + year + ",m=" + m + ",d=" + d);
        String result = year + "-" + m + "-" + d;
        return result;
    }


    /**
     * 获得当前指定格式的时间字符串
     *
     * @param formate 如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentWithFormate(String formate) {
        String time = "";
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 得到当前时间的Calendar形式
     *
     * @return Calendar
     */
    public static Calendar calGetCalendarTime() {
        Calendar caltime = Calendar.getInstance();
        return caltime;
    }

    /**
     * 获得系统默认格式的日期字符串
     *
     * @param date
     * @return
     */
    public static Date getSysDefaultDateTime(String datetime) {
        Date time = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(SYS_DATE_FORMATE);
            time = formatter.parse(datetime);
        } catch (Exception ex) {

        }
        return time;
    }

    /**
     * 获得系统默认格式的日期字符串
     *
     * @param date
     * @return
     */
    public static String getSysDateTime(Date date) {
        String time = null;
        SimpleDateFormat formatter = new SimpleDateFormat(SYS_DATE_FORMATE);
        time = formatter.format(date);
        return time;
    }

    /**
     * 根据日期字符串得到年，返回数字
     *
     * @param tempStr 时间字符串
     * @return
     */
    public static int intGetYear(String tempStr) {
        int dttime = 0;
        try {
            dttime = Integer.parseInt(tempStr.substring(0, 4));
        } catch (Exception ex) {

        }
        return dttime;
    }

    /**
     * 根据日期字符串得到月，返回数字
     *
     * @param tempStr
     * @return
     */
    public static int intGetMonth(String tempStr) {
        int dttime = 0;
        dttime = Integer.parseInt(tempStr.substring(5, 7));
        return dttime;
    }

    /**
     * 根据日期字符串得到日，返回数字
     * 2017-6-5
     *
     * @param tempStr 标准时间字符串
     * @return int
     */
    public static int intGetDay(String tempStr) {
        int dttime = 0;
        dttime = Integer.parseInt(tempStr.substring(8, 10));
        return dttime;
    }

    /**
     * 得到程序中标准时间到小时
     *
     * @return yyyy-MM-dd hh格式的时间
     */
    public static String strGetTimesToHour() {
        String time = "";
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh");
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 得到程序中标准时间到分
     *
     * @return yyyy-MM-dd hh:mm 格式的时间
     */
    public static String strGetTimeToMinute() {
        String time = null;
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 得到程序中标准时间到分
     *
     * @return yyyy年MM月dd日hh时mm分 格式的时间
     */
    public static String getTimeToMinute() {
        String time = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);  // 4 表示 2004 年
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        time = year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分";
        return time;
    }

    /**
     * 得到程序中标准时间到分
     *
     * @return yyyy年MM月dd日hh时mm分 格式的时间
     */
    public static String getTimeToSecond() {
        String time = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);  // 4 表示 2004 年
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        time = year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分" + second + "秒";
        return time;
    }

    /**
     * 得到程序中标准时间到秒
     *
     * @return yyyy-MM-dd HH:mm:ss 格式的时间
     */
    public static String strGetTime() {
        String time = "";
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 得到程序中标准时间到秒
     *
     * @return yyyy-MM-dd hh:mm:ss 格式的时间
     */
    public static String strGetTimeFull() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 得到程序中标准时间
     *
     * @return yyyy-MM-dd 格式的时间
     */
    public static String strGetTimes() {
        String time = "";
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 验证日期是否合法
     *
     * @param timeStr
     * @return
     */
    public static boolean isLeaglDate(String timeStr) {
        boolean flag = true;
        if (timeStr != null) {
            String check = "\\d{4}-\\d{2}-\\d{2}";
            Pattern timePar = Pattern.compile(check);
            Matcher matcher = timePar.matcher(timeStr);
            flag = matcher.matches();
        }
        return flag;
    }

    /**
     * 验证日期时间是否合法
     *
     * @param timeStr
     * @return
     */
    public static boolean isLeaglDateTime(String timeStr) {
        boolean flag = true;
        String check = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}((:| )\\d{3}+)?";
        Pattern timePar = Pattern.compile(check);
        Matcher matcher = timePar.matcher(timeStr);
        flag = matcher.matches() || isLeaglDate(timeStr);
        return flag;
    }

    /**
     * 如果数字长度小于2位则在前面补"0"
     *
     * @param param1
     * @return
     */
    public static String LenMore1(String param1) {
        if (param1.length() < 2) {
            param1 = "0" + param1;
        }
        return param1;
    }

    /**
     * 取时间中的月、日信息，组成short数值，“月*100+日”
     *
     * @param time 需要转换的时间，单位：秒
     * @return 如 1002 表示 10月2日
     */
    public static short getShortDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return ((short) (month * 100 + day));
    }

    /**
     * 取时间中的月、日信息，组成short数值，“(年-2000)*10000+月*100+日”
     *
     * @param time 需要转换的时间，单位：秒
     * @return 如 1002 表示 10月2日
     */
    public static int getIntDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        int year = calendar.get(Calendar.YEAR) - 2000;  // 4 表示 2004 年
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return ((int) (year * 10000 + month * 100 + day));
    }

    /**
     * 取时间中的时、分信息，组成short数值，“时*100+分”
     *
     * @param time 需要转换的时间，单位：秒
     * @return 如 2130 表示 21点30分
     */
    public static short getShortTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return ((short) (hour * 100 + minute));
    }

    /**
     * 取时间中的时、分、秒信息，组成short数值，“时*10000+分*100+秒”
     *
     * @param time 需要转换的时间，单位：秒
     * @return 如 213055 表示 21点30分55秒
     */
    public static int getIntTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return ((int) (hour * 10000 + minute * 100 + second));
    }


    /**
     * 将时间转换为显示字符串,“简短”格式 2004-8-16 12:8:1
     *
     * @param second 相对于 1970年1月1日零时的秒数
     * @return
     */
    public static String toTimeString(long second) {
        StringBuffer sbRet = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(second * 1000);

            sbRet.append(calendar.get(Calendar.YEAR)).append("-");
            sbRet.append(calendar.get(Calendar.MONTH) + 1).append("-");
            sbRet.append(calendar.get(Calendar.DATE)).append(" ");
            sbRet.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
            sbRet.append(calendar.get(Calendar.MINUTE)).append(":");
            sbRet.append(calendar.get(Calendar.SECOND));
        } catch (Exception e) {/* DISCARD EXEPTION */
        }
        return sbRet.toString();
    }

    /**
     * 将时间转换为显示字符串，“全长”格式 2004-08-16 12:08:01
     *
     * @param second 相对于 1970年1月1日零时的秒数
     * @return
     */
    public static String toTimeFullString(long second) {
        StringBuffer sbRet = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(second * 1000);

            sbRet.append(calendar.get(Calendar.YEAR)).append("-");
            sbRet.append(integerToString((calendar.get(Calendar.MONTH) + 1), 2)).append("-");
            sbRet.append(integerToString(calendar.get(Calendar.DATE), 2)).append(" ");
            sbRet.append(integerToString(calendar.get(Calendar.HOUR_OF_DAY), 2)).append(":");
            sbRet.append(integerToString(calendar.get(Calendar.MINUTE), 2)).append(":");
            sbRet.append(integerToString(calendar.get(Calendar.SECOND), 2));
        } catch (Exception e) {/* DISCARD EXEPTION */
        }
        return sbRet.toString();
    }

    /**
     * 将时间转换为显示字符串，“全长”格式 20040816120801
     *
     * @param second 相对于 1970年1月1日零时的秒数
     * @return
     */
    public static String toTimeFullString2(long second) {
        StringBuffer sbRet = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(second * 1000);

            sbRet.append(calendar.get(Calendar.YEAR)).append("");
            sbRet.append(integerToString((calendar.get(Calendar.MONTH) + 1), 2)).append("");
            sbRet.append(integerToString(calendar.get(Calendar.DATE), 2)).append("");
            sbRet.append(integerToString(calendar.get(Calendar.HOUR_OF_DAY), 2)).append("");
            sbRet.append(integerToString(calendar.get(Calendar.MINUTE), 2)).append("");
            sbRet.append(integerToString(calendar.get(Calendar.SECOND), 2));
        } catch (Exception e) {/* DISCARD EXEPTION */
        }
        return sbRet.toString();
    }

    /**
     * 将时间字符串转化为秒值（相对 1970年１月１日）
     *
     * @param time   时间字符串
     * @param format 格式串，如　"yyyy-MM-dd-HH-mm", "yyyy-MM-dd HH:mm:ss"
     * @return　　　失败返回　０
     */
    public static int toTimeInteger(String time, String format) {
        int nRet = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date de = dateFormat.parse(time);
            nRet = (int) (de.getTime() / 1000);
        } catch (Exception e) {/*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 获得时间
     *
     * @param date
     * @return
     */
    public static long toTimeLong(Date date) {
        try {
            return date.getTime();
        } catch (Exception ex) {

            return 0;
        }
    }

    /**
     * 将时间字符串转化为毫秒值（相对 1970年１月１日）
     *
     * @param time 时间字符串
     * @return　　　失败返回　０
     */
    public static long toSystemTimeLongMi(String time) {
        long nRet = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(SYS_DATE_FORMATE);
            Date de = dateFormat.parse(time);
            nRet = de.getTime();
        } catch (Exception e) {/*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 将时间字符串转化为秒值（相对 1970年１月１日）
     *
     * @param time 时间字符串
     * @return　　　失败返回　０
     */
    public static long toSystemTimeLong(String time) {
        long nRet = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(SYS_DATE_FORMATE);
            Date de = dateFormat.parse(time);
            nRet = de.getTime() / 1000;
        } catch (Exception e) {/*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 将时间字符串转化为秒值（相对 1970年１月１日）
     *
     * @param time   时间字符串
     * @param format 格式串，如　"yyyy-MM-dd-HH-mm", "yyyy-MM-dd HH:mm:ss"
     * @return　　　失败返回　０
     */
    public static long toTimeLong(String time, String format) {
        long nRet = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date de = dateFormat.parse(time);
            nRet = de.getTime() / 1000;
        } catch (Exception e) {/*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 将时间转换为显示字符串 "19990412"
     *
     * @param second 相对于 1970年1月1日零时的秒数
     * @return
     */
    public static String toDateString(long second) {
        StringBuffer sbRet = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(second * 1000);

            sbRet.append(calendar.get(Calendar.YEAR));
            if (calendar.get(Calendar.MONTH) + 1 < 10) {
                sbRet.append("0");
            }
            sbRet.append(calendar.get(Calendar.MONTH) + 1);
            if (calendar.get(Calendar.DATE) < 10) {
                sbRet.append("0");
            }
            sbRet.append(calendar.get(Calendar.DATE));
        } catch (Exception e) {/* DISCARD EXEPTION */
        }
        return sbRet.toString();
    }

    /**
     * 将输入的整数转换为指定长度的字符串，不足时首部用'0'填充, 整数超长时则直接输出
     *
     * @param input_num     输入的整数
     * @param output_length 输出字符串的长度
     * @return 参数(32, 4），输出：“0032”，参数(3390, 3)，输出：“3390”
     */
    public static String integerToString(int input_num, int output_length) {
        StringBuffer sbPrefix = new StringBuffer();
        String sTempInput = "" + input_num;

        if (sTempInput.length() < output_length) {
            for (int i = 0; i < output_length - sTempInput.length(); i++) {
                sbPrefix.append('0');
            }
        }

        return sbPrefix.append(sTempInput).toString();
    }

    /**
     * 将时间字符串换算成相对于零点的秒数
     * "03:23:10"  三时二十三分十秒
     * "15:00:00"  十五时
     */
    public static int getDayTime(String time) {
        int nRet = 0;
        try {
            String[] secTime = time.split(":");
            for (int i = 0; i < secTime.length; i++) {
                int nTemp = Integer.parseInt(secTime[i]);  /* 时:分:秒*/
                for (int ii = 0; ii < 2 - i; ii++) {
                    nTemp = nTemp * 60;
                }
                nRet += nTemp;
            }
        } catch (Exception e) {
            /*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 将时间长度整数值转换成 时分秒 的可读字符串
     *
     * @param ltime
     * @return
     */
    public static String getTimeString(long ltime) {
        return getDayTimeString_inner(ltime, true);
    }

    /**
     * 根据时间戳,获取当前时分秒时间字符串,不包含日期信息
     *
     * @param ltime
     * @return
     */
    public static String getDayTimeString(long ltime) {
        return getDayTimeString_inner(ltime, false);
    }

    /**
     * 根据时间戳,获取当前时分秒时间字符串,不包含日期信息
     *
     * @param ltime 格式为yyyy-mm-dd HH:mm:ss 格式的时间字符串
     * @return
     */
    public static String getDateTimeWithOutSenondString(String ltime) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(SYS_DATE_FORMATE);
            Date time = formatter.parse(ltime);

            SimpleDateFormat formater2 = new SimpleDateFormat(TIME_WITH_SECOND_FORMATE);
            result = formater2.format(time);
        } catch (Exception ex) {
            return ltime;
        }
        return result;
    }

    /**
     * 时间格式转换
     *
     * @param ltime - 相对于当天零时的秒数
     * @return String - 带格式的时间字符串 “时:分:秒”
     */
    private static String getDayTimeString_inner(long ltime, boolean zone_flag) {
        long daytime = ltime % (24 * 3600);        // 将时间折算为一天之内
        Date dateTemp = new Date(daytime * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        if (zone_flag) {
            formatter.setTimeZone(new SimpleTimeZone(0, "")); // 不应该是零时区
        }
        return formatter.format(dateTemp);
    }

    /**
     * 时间格式转换
     *
     * @param ltime  - 相对于1970年1月1日零时的秒数
     * @param format - 格式字符串
     * @return String - 带格式的时间字符串
     */
    public static String getTimeWithFormat(long ltime, String format) {
        Date dateTemp = new Date(ltime * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(dateTemp);
    }

    /**
     * 时间格式转换
     *
     * @param ltime - yyyy-MM-dd HH:mm:ss
     * @return String - yyyy年MM月dd日 HH时:mm分
     */
    public static String getTimeWithFormat(String ltime) {
        String ftime = "";
        if (ltime.length() >= 18) {
            ftime = ltime.substring(0, 4) + "年" + ltime.substring(5, 7) + "月"
                    + ltime.substring(8, 10) + "日" + ltime.substring(11, 13) + "时"
                    + ltime.substring(14, 16) + "分";
        }
        return ftime;

    }


    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数 1 格式：2011-11-11 11:11:11
     * @param str2 时间参数 2 格式：2011-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long time1 = toSystemTimeLong(str1);
        long time2 = toSystemTimeLong(str2);
        long diff = 0;
        if (time1 < time2) {
//			diff = time2 - time1;
            return min + "分";
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60);
        hour = (diff / (60 * 60) - day * 24);
        min = ((diff / (60)) - day * 24 * 60 - hour * 60);
        if (day == 0) {
            if (hour == 0) {
                return min + "分";
            } else {
                return hour + "小时" + min + "分";
            }
        } else {
            return day + "天" + hour + "小时" + min + "分";
        }
    }

    /**
     * 得出2个时间相差多少分钟
     *
     * @param ltime -
     * @param str1  时间参数 1 格式：2011-11-11 11:11:11
     * @param str2  时间参数 2 格式：2011-01-01 12:00:00
     * @return boolean
     */
    public static Long getDistanceMin(String now, String callTime) {
        long min = -1;
        long time1 = toSystemTimeLong(now);
        long time2 = toSystemTimeLong(callTime);
        min = ((time1 - time2) / (60));
        return min;
    }

    /**
     * 得出2个时间相差多少秒
     *
     * @return boolean
     */
    public static Long getDistanceSec(String now, String callTime) {
        long second = -1;
        long time1 = toSystemTimeLong(now);
        long time2 = toSystemTimeLong(callTime);
        second = (time1 - time2);
        return second;
    }

    /**
     * 比较时间先后
     *
     * @param ltime -
     * @param str1  时间参数 1 格式：2011-11-11 11:11:11
     * @param str2  时间参数 2 格式：2011-01-01 12:00:00
     * @return boolean
     */
    public static boolean getDistance(String str1, String str2) {
        boolean result = false;
        long time1 = toSystemTimeLong(str1);
        long time2 = toSystemTimeLong(str2);
        if (time1 > time2) {
            result = true;
        }
        return result;
    }

    /**
     * 获取某个时间后的n小时
     *
     * @return
     */
    public static String getTimeLaterH(String time, int n) {

        long currentTime = toSystemTimeLongMi(time);
        currentTime += n * 60 * 60 * 1000;
        Date date = new Date(currentTime);
        return getSysDateTime(date);
    }

    /**
     * 获取当前系统时间后的n分钟
     *
     * @return
     */
    public static String endTime(int n) {
        long currentTime = System.currentTimeMillis();
        currentTime += n * 60 * 1000;
        Date date = new Date(currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return StringFilter(dateFormat.format(date.getTime()).replaceAll("\\s+", ""));
    }

    /**
     * 获取该时间后的n秒钟
     *
     * @return
     */
    public static String getTimeLaterS(String time, int n) {

        long currentTime = toSystemTimeLongMi(time);
        if (currentTime != 0) {
            currentTime += n * 1000;
            Date date = new Date(currentTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return getSysDateTime(date);
        }
        return time;

    }

    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[:-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String formatCST(String time) {
        String formatTime = strGetTimeToMinute() + ":00";
//		SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//	       try
//	       {
//	       	   Date date=sdf1.parse(time.toString());
////	       	   Date date=(Date) sdf1.parseObject(time);
//	           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	           formatTime=sdf.format(date);
//	       }
//	       catch (Exception e)
//	       {
//	           e.printStackTrace();
//	       }
        try {
            String month = time.substring(4, 7);
            String day = time.substring(8, 10);
            String timeDetail = time.substring(11, 19);
            String year = time.substring(24);
            if ("Jan".equals(month)) {
                month = "01";
            } else if ("Feb".equals(month)) {
                month = "02";
            } else if ("Mar".equals(month)) {
                month = "03";
            } else if ("Apr".equals(month)) {
                month = "04";
            } else if ("May".equals(month)) {
                month = "05";
            } else if ("Jun".equals(month)) {
                month = "06";
            } else if ("Jul".equals(month)) {
                month = "07";
            } else if ("Aug".equals(month)) {
                month = "08";
            } else if ("Sep".equals(month)) {
                month = "09";
            } else if ("Oct".equals(month)) {
                month = "10";
            } else if ("Nov".equals(month)) {
                month = "11";
            } else if ("Dec".equals(month)) {
                month = "12";
            }

            String splitTime = year + "-" + month + "-" + day + " " + timeDetail;
            System.out.println(splitTime);
            boolean b = isLeaglDateTime(splitTime);
            if (b) {
                formatTime = splitTime;
            }
        } catch (Exception e) {
        }
        return formatTime;

    }

    /**
     * 获取本月的第一天
     *
     * @return
     */
    public static String getFirstDay() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        String[] strs = time.split("-");
        return strs[0] + "-" + strs[1] + "-01";
    }

//	/**
//	 * 日期选择器
//	 * @param context
//	 * @param dataView
//     * @return
//     */
//	public static AlertDialog datePickerDialog(Context context,
//												   final TextView dataView) {
//		int year1 = Calendar.getInstance().get(Calendar.YEAR);
//		int month1 = Calendar.getInstance().get(Calendar.MONTH) + 1;
//		int day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		String oldTime = dataView.getText().toString().trim();
//		if (!StringUtils.isEmpty(oldTime)){
//			try {
//				year1 = intGetYear(oldTime);
//				month1 = intGetMonth(oldTime);
//				day1 = intGetDay(oldTime);
//			} catch (Exception e) {
//				year1 = Calendar.getInstance().get(Calendar.YEAR);
//				month1 = Calendar.getInstance().get(Calendar.MONTH) + 1;
//				day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//			}
//
//		}
//		final Calendar time = Calendar.getInstance(Locale.CHINA);
//		final SimpleDateFormat format = new SimpleDateFormat(
//				"yyyy-MM-dd");
//		LinearLayout dateTimeLayout = (LinearLayout) LayoutInflater.from(
//				context).inflate(R.layout.date_time_dialog, null);
//		final DatePicker datePicker = (DatePicker) dateTimeLayout
//				.findViewById(R.id.DatePicker);
//		final TimePicker timePicker = (TimePicker) dateTimeLayout
//				.findViewById(R.id.TimePicker);
//
//		timePicker.setVisibility(View.GONE);
//
//		DatePicker.OnDateChangedListener dateListener = new DatePicker.OnDateChangedListener() {
//
//			@Override
//			public void onDateChanged(DatePicker view, int year,
//									  int monthOfYear, int dayOfMonth) {
//				time.set(Calendar.YEAR, year);
//				time.set(Calendar.MONTH, monthOfYear );
//				time.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//				Log.v("zbj", "monthOfYear = " + monthOfYear);
//			}
//		};
//
//		datePicker.init(year1, month1-1,
//				day1, dateListener);
//
//		AlertDialog dialog = new AlertDialog.Builder(context)
//				.setTitle("设置日期时间")
//				.setView(dateTimeLayout)
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						datePicker.clearFocus();
//						timePicker.clearFocus();
//						time.set(Calendar.YEAR, datePicker.getYear());
//						time.set(Calendar.MONTH, datePicker.getMonth());
//						time.set(Calendar.DAY_OF_MONTH,
//								datePicker.getDayOfMonth());
//						dataView.setText(format.format(time.getTime()));
//					}
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				}).show();
//		return dialog;
//	}

//	/**
//	 * 日期时间选择器
//	 * @param context
//	 * @param dataView
//     * @return
//     */
//	public static AlertDialog dateTimePickerDialog(Context context,
//												   final TextView dataView) {
//		int year1 = Calendar.getInstance().get(Calendar.YEAR);
//		int month1 = Calendar.getInstance().get(Calendar.MONTH);
//		int day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		int hour1 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//		int minute1 = Calendar.getInstance().get(Calendar.MINUTE);
//		int second1 = Calendar.getInstance().get(Calendar.SECOND);
//		String oldTime = dataView.getText().toString().trim();
//		if (!StringUtils.isEmpty(oldTime)){
//			String[] dateTimeGroup = oldTime.split(" ");
//			String[] dateGroup = dateTimeGroup[0].split("-");
//			String[] timeGroup = dateTimeGroup[1].split(":");
//			year1 = Integer.parseInt(dateGroup[0]);
//			month1 = Integer.parseInt(dateGroup[1]);
//			day1 = Integer.parseInt(dateGroup[2]);
//			hour1 = Integer.parseInt(timeGroup[0]);
//			minute1 = Integer.parseInt(timeGroup[1]);
//			second1 = Integer.parseInt(dateGroup[2]);
//		}
//		final Calendar time = Calendar.getInstance(Locale.CHINA);
//		final SimpleDateFormat format = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		LinearLayout dateTimeLayout = (LinearLayout) LayoutInflater.from(
//				context).inflate(R.layout.date_time_dialog, null);
//		final DatePicker datePicker = (DatePicker) dateTimeLayout
//				.findViewById(R.id.DatePicker);
//		final TimePicker timePicker = (TimePicker) dateTimeLayout
//				.findViewById(R.id.TimePicker);
//		// if(dataView == null)
//		timePicker.setIs24HourView(true);
//
//		TimePicker.OnTimeChangedListener timeListener = new TimePicker.OnTimeChangedListener() {
//
//			@Override
//			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//				time.set(Calendar.HOUR_OF_DAY, hourOfDay);
//				time.set(Calendar.MINUTE, minute);
//
//			}
//		};
//
//		timePicker.setOnTimeChangedListener(timeListener);
//
//		DatePicker.OnDateChangedListener dateListener = new DatePicker.OnDateChangedListener() {
//
//			@Override
//			public void onDateChanged(DatePicker view, int year,
//									  int monthOfYear, int dayOfMonth) {
//				time.set(Calendar.YEAR, year);
//				time.set(Calendar.MONTH, monthOfYear);
//				time.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//				Log.v("zbj", "monthOfYear = " + monthOfYear);
//			}
//		};
//
//		datePicker.init(year1, month1-1,
//				day1, dateListener);
//		timePicker.setCurrentHour(hour1);
//		timePicker.setCurrentMinute(minute1);
//
//		AlertDialog dialog = new AlertDialog.Builder(context)
//				.setTitle("设置日期时间")
//				.setView(dateTimeLayout)
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						datePicker.clearFocus();
//						timePicker.clearFocus();
//						time.set(Calendar.YEAR, datePicker.getYear());
//						time.set(Calendar.MONTH, datePicker.getMonth());
//						time.set(Calendar.DAY_OF_MONTH,
//								datePicker.getDayOfMonth());
//						time.set(Calendar.HOUR_OF_DAY,
//								timePicker.getCurrentHour());
//						time.set(Calendar.MINUTE, timePicker.getCurrentMinute());
//						time.set(Calendar.SECOND, 00);
//						dataView.setText(format.format(time.getTime()));
//					}
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//
//					}
//				}).show();
//		return dialog;
//	}
//


    public static void main(String[] args) {

//		String stime=getCurrentWithFormate("yyyyMMddHHmmss");
        String time = "Thu Apr 07 19:04:00 CST 2016";
        String stime = formatCST(time);

        String time2 = "2016-04-07 19:04:00";

        System.out.println(stime);
//		System.out.println(toSystemTimeLongMi(stime));
//		System.out.println(toSystemTimeLongMi(time2));
//		System.out.println(toSystemTimeLongMi(stime) - toSystemTimeLongMi(time2));
    }

    public static String secondToMinuteOrHour(long seconds) {
        //60S之内
        if (seconds < 60) {
            return String.format("00:%s", seconds < 10 ? "0" + seconds : seconds);
        } else {
            //60分钟以内
            if (seconds / 60 < 60) {
                return String.format("%s:%s", (seconds / 60) < 10 ? "0" + seconds / 60 : seconds / 60, seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60);
            } else {
                //60分钟之后
                return String.format("%s:%s:%s", seconds / 360,
                        (seconds - (seconds / 360) * 360) / 60 < 10 ? "0" + (seconds - (seconds / 360) * 360) / 60 : (seconds - (seconds / 360) * 360) / 60,
                        seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60);
            }
        }
    }
}
