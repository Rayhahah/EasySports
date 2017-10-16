package com.rayhahah.rbase.utils.base;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具集合
 *
 * @author Administrator
 */
public class StringUtils {

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 字符串在某个长度范围内
     *
     * @param str
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean strIsBetween(String str, int minLength, int maxLength) {
        if (StringUtils.isEmpty(str) || minLength > maxLength) {
            return false;
        }
        int len = str.length();
        return len >= minLength && len <= maxLength;
    }

    /**
     * 判断长度是否满足需要的长度
     *
     * @param str    字符串
     * @param length 要求的长度
     * @return true 满足 false 不满足
     */
    public boolean isLegalLength(String str, int length) {
        boolean flag = false;
        if (str != null && str.trim().length() == length) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email 邮箱地址
     * @return boolean
     */
    public static boolean isEmail(String email) {
        email = email != null ? email.trim() : null;
        if (email == null) {
            return false;
        } else if (email.indexOf(' ') != -1) {
            return false;
        } else {
            int idx = email.indexOf('@');
            if (idx == -1 || idx == 0 || (idx + 1) == email.length()) {
                return false;
            }
            if (email.indexOf('@', idx + 1) != -1) {
                return false;
            }
            if (email.indexOf('.') == -1) {
                return false;
            }
            return true;
        }
        /*
         * Pattern emailer=null; if(email!=null){ String check =
		 * "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
		 * ; emailer = Pattern.compile(check); Matcher matcher =
		 * emailer.matcher(email); return matcher.matches(); }else{ return
		 * false; }
		 */
    }

    /***
     * 是否是合法手机号
     *
     * @param phone 手机号
     * @return true 合法手机号 ，false 不合法手机号
     */
    public static boolean isLegalPhone(String phone) {
        boolean flag = false;
        if (phone != null && phone.trim().length() == 11) {
            String check = "1\\d{10}";
            Pattern pattern = Pattern.compile(check);
            Matcher matcher = pattern.matcher(phone);
            flag = matcher.matches();
        }
        return flag;
    }

    /**
     * 判断字符串是否是字母和数字的组合
     *
     * @param str
     * @return
     */
    public static boolean strIsLetterOrNumer(String str) {
        boolean flag = false;
        if (isNotEmpty(str)) {
            String check = "^[A-Za-z0-9]{1,}$";
            Pattern pattern = Pattern.compile(check);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        }
        return flag;
    }

    /**
     * 是否是合法的UID
     *
     * @param uid
     * @return
     */
    public static boolean isLegalUserId(String uid) {
        boolean flag = false;
        if (uid != null) {
            String check = "\\d{1,}";
            Pattern pattern = Pattern.compile(check);
            Matcher matcher = pattern.matcher(uid);
            flag = matcher.matches();
        }
        return flag;
    }

    /**
     * 是否是合法电话
     *
     * @param tel 电话号码
     * @return true　合法电话号码，false 不合法电话号码
     */
    public static boolean isLegalTel(String tel) {
        boolean flag = false;
        if (tel != null) {
            String check = "((d{3,4})|d{3,4}-)?d{7,8}(-d{3})*";
            Pattern pattern = Pattern.compile(check);
            Matcher matcher = pattern.matcher(tel);
            flag = matcher.matches();
            flag = flag | isLegalPhone(tel);
        }
        return flag;

    }

    /**
     * 用户名必须是数字或者字母的结合
     *
     * @param username 字符串
     * @return boolean
     */
    public static boolean isLegalUsername(String username) {
        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);
            if (!isAscii(ch) && ch != '.' && ch != '_' && ch != '-'
                    && ch != '+' && ch != '(' && ch != ')' && ch != '*'
                    && ch != '^' && ch != '@' && ch != '%' && ch != '$'
                    && ch != '#' && ch != '~' && ch != '-') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是字母和数字的结合
     *
     * @param name 字符串
     * @return boolean
     */
    public static boolean isAsciiOrDigit(String name) {
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (!isAscii(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转换成gb2312 形式的字符串
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String iso2Gb(String str) {
        String sRet = "";
        str = str == null ? "" : str;
        try {
            sRet = new String(str.getBytes("ISO8859_1"), "GB2312");
        } catch (Exception ex) {

        }
        return sRet;
    }

    /**
     * 字符串转换成gbk 形式的字符串
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String iso2Gbk(String str) {
        String sRet = "";
        str = str == null ? "" : str;
        try {
            sRet = new String(str.getBytes("ISO8859_1"), "GBK");
        } catch (Exception ex) {

        }
        return sRet;
    }

    /**
     * 字符串转换成utf-8形式的字符串
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String isoToUtf(String str) {
        String sRet = "";
        str = str == null ? "" : str;
        try {
            sRet = new String(str.getBytes("ISO8859_1"), "UTF-8");
        } catch (Exception ex) {

        }
        return sRet;
    }

    /**
     * 将指定字符串转化为int
     *
     * @param sStr 转换的字符串
     * @return int
     */
    public static int strToInt(String sStr) {
        try {
            if (isEmpty(sStr)) {
                return 0;
            }
            return Integer.parseInt(sStr);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将指定字符串转化为long
     *
     * @param sStr 待转换的字符串
     * @return 返回简单类型类型long的变量
     */
    public static long strToLong(String sStr) {
        try {
            if (isEmpty(sStr)) {
                return 0;
            }
            return Long.parseLong(sStr);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将指定字符串转化为long
     *
     * @param sStr 待转换的字符串
     * @return 返回简单类型类型long的变量
     */
    public static byte strToByte(String sStr) {
        byte b = 0;
        if (isEmpty(sStr)) {
            return b;
        }
        try {
            b = Byte.parseByte(sStr);
        } catch (Exception ex) {

        }
        return b;
    }

    /**
     * 将指定字符串转化为long
     *
     * @param sStr 待转换的字符串
     * @return 返回简单类型类型long的变量
     */
    public static boolean strToBoolean(String sStr) {
        if (isEmpty(sStr)) {
            return false;
        }
        try {
            return Boolean.parseBoolean(sStr);
        } catch (Exception ex) {
        }
        return false;
    }

    /**
     * 将整数转换成字符串
     *
     * @param nValue
     * @return string
     */
    public static String intToStr(int nValue) {
        return new Integer(nValue).toString();
    }

    /**
     * 将整数转换成字符串
     *
     * @param nValue
     * @return string
     */
    public static String longToStr(long nValue) {
        return new Long(nValue).toString();
    }

    /**
     * 将字符串转换成float型。
     *
     * @param sStr
     * @return float
     */

    public static float strToFloat(String sStr) {
        if (sStr == null || "".equals(sStr)) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(sStr);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将字符串转换成double型。
     *
     * @param sStr
     * @return double
     */

    public static double strToDouble(String sStr) {
        if (sStr == null || "".equals(sStr)) {
            return 0.0;
        }
        try {
            return Double.parseDouble(sStr);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 判断某个值是否为空值
     *
     * @param Value
     * @return boolean
     */
    public static boolean isEmpty(String Value) {
        return (Value == null || "".equals(Value.trim()));
    }

    /**
     * 判断某个值是否为空值
     *
     * @param Value
     * @return boolean
     */
    public static boolean isNotEmpty(String Value) {
        return !isEmpty(Value);
    }

    /**
     * 文件路径转换
     *
     * @param value 文件路径字符串
     * @return String
     */
    public static String replacePath(String value) {
        String path = "";
        try {
            path = value;
            path = path.replaceAll("\\\\", "/");
        } catch (Exception ex) {
        }
        return path;
    }

    /**
     * 针对wap 版本封装相应的url
     *
     * @param url
     * @param query
     * @return
     */
    public static String getUrlForReqQuery(String url, String query) {
        StringBuffer sb = new StringBuffer();
        try {
            if (url != null && url.indexOf("?") > -1 && query != null) {
                url = url.replaceAll("\\?", "\\&");
                url = url.indexOf("&amp;") > -1 ? url : url.replaceAll("\\&",
                        "\\&amp;");
                query = query.indexOf("&amp;") > -1 ? query : query.replaceAll(
                        "\\&", "\\&amp;");
                sb.append(url).append("&amp;").append(query);
            } else if (url != null && query != null) {
                url = url.indexOf("&amp;") > -1 ? url : url.replaceAll("\\&",
                        "\\&amp;");
                query = query.indexOf("&amp;") > -1 ? query : query.replaceAll(
                        "\\&", "\\&amp;");
                sb.append(url).append("&amp;").append(query);
            } else if (url != null) {
                sb.append(url);
            }
        } catch (Exception ex) {
            sb.append(url);
        }
        return sb.toString();
    }

    /**
     * 字符串过长,就截取一部分,其他的用append 字符串 显示
     *
     * @param str   需要被转换的字符串
     * @param count 最终显示字符数
     * @return String
     * @parm append 附加字符串
     */
    public static String appendExtStr(String str, int count, String append) {
        if (!"".equals(str) && str != null && str.length() > count) {
            str = str.substring(0, count).concat(append);
        }
        return str;
    }

    /**
     * 将数据库提取的结果转换成字符串
     *
     * @param obj 数据库提取结果对象
     * @return String
     */
    public static String getStrForDb(Object obj) {
        String result = "";
        result = obj != null ? obj.toString() : result;
        return result;
    }

    /**
     * 将数据库提取的结果转换成int
     *
     * @param obj 数据库提取结果对象
     * @return String
     */
    public static int getIntForDb(Object obj) {
        int result = 0;
        if (obj != null) {
            try {
                result = Integer.parseInt(obj.toString());
            } catch (Exception ex) {
            }
        }
        return result;
    }

    /**
     * 将数据库提取的结果转换成Long
     *
     * @param obj 数据库提取结果对象
     * @return String
     */
    public static long getLongForDb(Object obj) {
        long result = 0;
        if (obj != null) {
            try {
                result = Long.parseLong(obj.toString());
            } catch (Exception ex) {
            }
        }
        return result;
    }

    /**
     * 将数据转换成float
     *
     * @param object
     * @return
     */
    public static float getFloatForDb(Object object) {
        float result = 0;
        try {
            result = Float.parseFloat(object.toString());
        } catch (Exception ex) {
        }
        return result;
    }

    /**
     * 字符是否是ascil码
     *
     * @param ch 字符
     * @return boolean
     */
    private static boolean isAscii(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9');
    }

    /**
     * 判断出发时间距离到达时间是否超过30分钟
     *
     * @param goTime 出发时间，arriveTime 到达时间
     * @return boolean
     */
    private static boolean isTimeOut(String goTime, String arriveTime) {
        String[] goTimePart = goTime.split(" ");
        String[] goTimePart1 = goTimePart[0].split("-");
        String[] goTimePart2 = goTimePart[0].split(":");

        String[] arriveTimePart = arriveTime.split(" ");
        String[] arriveTimePart1 = arriveTimePart[0].split("-");
        String[] arriveTimePart2 = arriveTimePart[0].split(":");

        if (Integer.parseInt(arriveTimePart1[0]) > Integer
                .parseInt(goTimePart1[0])) {
            return true;
        } else if (Integer.parseInt(arriveTimePart1[1]) > Integer
                .parseInt(goTimePart1[1])) {
            return true;
        } else if (Integer.parseInt(arriveTimePart1[2]) > Integer
                .parseInt(goTimePart1[2])) {
            return true;
        } else if (((Integer.parseInt(arriveTimePart2[0]) - Integer
                .parseInt(goTimePart2[0])) * 60 + (Integer
                .parseInt(arriveTimePart2[1]) - Integer
                .parseInt(goTimePart2[1]))) > 30) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    public static void copy(Context mContext, String stripped) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("content", stripped);
        clipboard.setPrimaryClip(clip);
    }

}