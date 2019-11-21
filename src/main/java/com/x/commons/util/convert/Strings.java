package com.x.commons.util.convert;

import com.x.commons.enums.Charsets;
import com.x.commons.enums.DisplayStyle;
import com.x.commons.enums.Regex;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 字符串工具类
 *
 * @Author AD
 * @Date 2018/12/19 12:45
 */
public final class Strings {

    // ======================== 变量 ========================

    /**
     * 空字符串
     */
    private static final String NULL = "";

    /**
     * 随机变量
     */
    private static final Random RANDOM = new Random();

    /**
     * 16进制字符串(大写)
     */
    private static final String HEX = "0123456789ABCDEF";

    /**
     * 16进制字符串(大小写)
     */
    private static final String ALL_HEX = "0123456789ABCDEFabcdef";

    /**
     * 数字、大小写字母
     */
    private static final char[] KEY_WORDS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    /**
     * 空字节数组
     */
    private static final byte[] EMPTY_BYTE = new byte[0];

    // ====================== 构造方法 =======================

    private Strings() {}

    // ======================== API ========================

    public static byte toByte(String value) {
        return Byte.valueOf(value);
    }

    public static short toShort(String value) {return Short.valueOf(value);}

    public static int toInt(String value) {return Integer.valueOf(value);}

    public static long toLong(String value) { return Long.valueOf(value);}

    public static float toFloat(String value) { return Float.valueOf(value);}

    public static double toDouble(String value) { return Double.valueOf(value);}

    /**
     * toString方法，默认多行显示
     *
     * @param o 需要重写toString方法的对象
     *
     * @return
     */
    public static String defaultToString(Object o) {
        return o == null ? "" : reflectToString(o, DisplayStyle.MULTI_LINE);
    }

    /**
     * toString方法
     *
     * @param o     需要重写toString方法的对象
     * @param style 显示风格
     *
     * @return
     */
    public static String reflectToString(Object o, DisplayStyle style) {
        return ToStringBuilder.reflectionToString(o, style.get());
    }

    /**
     * 判断字符串是否为null或者""
     *
     * @param check 需检查的字符串
     *
     * @return boolean
     *
     * @author AD
     * @date 2018-12-22 18:29
     */
    public static boolean isNull(String check) {

        return check == null || check.trim().length() == 0;
    }

    /**
     * 判断字符串是否为null、""、" "、或者被认为是null的字符串
     *
     * @param check 需检查的字符串
     * @param nulls 被认为是null的字符串(如：{},"null")
     *
     * @return boolean
     *
     * @author AD
     * @date 2018-12-22 18:57
     */
    public static boolean isNull(String check, String... nulls) {

        return isNull(check) || Stream.of(nulls).anyMatch(n -> check.equals(n));
    }

    /**
     * 判断字符串是否为null或者""（包含"null","{}"）
     *
     * @param check 需检查的字符串
     *
     * @return boolean
     *
     * @author AD
     * @date 2018-12-22 18:29
     */
    public static boolean isNullStr(String check) {

        return isNull(check, "null", "{}");
    }

    /**
     * 转换成大写
     *
     * @param convert
     *
     * @return
     */
    public static String toUppercase(Object convert) {
        return convert == null ? "" : String.valueOf(convert).toUpperCase();
    }

    /**
     * 16 进制字符串 --> byte[],如果包含其它字符(空格除外)，将放回空数组
     *
     * @param hex 16 进制字符串
     *
     * @return byte[]
     *
     * @author AD
     * @date 2018-12-22 18:29
     */
    public static byte[] hexToBytes(@NonNull String hex) {

        String s = removeSpaces(hex);
        return onlyHex(s) ? getBytes(fix(s)) : EMPTY_BYTE;
    }

    /**
     * 任意的字符串 --> byte[](UTF-8编码)
     *
     * @param context 需转换的字符串
     *
     * @return byte[]
     *
     * @author AD
     * @date 2018-12-22 18:29
     */
    public static byte[] toBytes(@NonNull String context) {

        return toBytes(context, Charsets.UTF8);
    }

    /**
     * 任意的字符串 --> byte[]
     *
     * @param context 需转换的字符串
     * @param charset 字符编码(如：Charsets.UTF8)
     *
     * @return byte[]
     *
     * @author AD
     * @date 2018-12-22 18:30
     */

    public static byte[] toBytes(@NonNull String context, Charset charset) {

        return context.getBytes(charset);
    }

    /**
     * int 值 --> 16 进制字符串
     *
     * @param v int值
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:30
     */
    public static String toHex(int v) {

        return fix(Integer.toHexString(v).toUpperCase());
    }

    /**
     * byte[] --> 16 进制字符串
     *
     * @param bs byte[]
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:30
     */
    public static String toHex(@NonNull byte[] bs) {

        return toHex(bs, "");
    }

    /**
     * byte[] --> 16 进制字符串，并用分隔符分隔每一个字节
     *
     * @param bs        byte[]
     * @param separator 分隔符
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:31
     */
    public static String toHex(@NonNull byte[] bs, String separator) {

        final SB sb = New.sb();
        for (byte b : bs) {
            sb.append(toHex((b & 0xFF))).append(separator);
        }
        return sb.get();
    }

    /**
     * byte[] --> ASCII 字符串
     *
     * @param bs byte[]
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:31
     */
    public static String toASCII(@NonNull byte[] bs) {

        return new String(bs, Charsets.US_ASCII);
    }

    /**
     * 16进制字符串转ASCII码
     *
     * @param hex 16进制字符串
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:32
     */
    public static String toASCII(@NonNull String hex) {

        return toASCII(toBytes(hex));
    }

    /**
     * byte[] --> 字符串，UTF-8编码
     *
     * @param bs byte[]
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:32
     */
    public static String toUTF8(@NonNull byte[] bs) {

        return decode(bs, Charsets.UTF8);
    }

    /**
     * byte[] --> 字符串，指定编码方式
     *
     * @param bs      byte[]
     * @param charset
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:33
     */
    public static String decode(@NonNull byte[] bs, Charset charset) {

        return new String(bs, charset);
    }

    /**
     * 用参数内容替换占位符
     *
     * @param pattern 如：{0}是中国人,来自{1},{2}岁
     * @param params
     *
     * @return
     */
    public static String replace(String pattern, Object... params) {
        if (isNull(pattern)) return "";
        return MessageFormat.format(pattern, params);
    }

    /**
     * 获取异常跟踪信息字符串
     *
     * @param throwable
     *
     * @return
     */
    public static String getExceptionTrace(Throwable throwable) {
        if (throwable == null) return "none";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 获取32位长度随机字符串
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 获取指定长度的随机字符串，包含数字、大小写字母
     *
     * @param length     指定长度
     * @param onlyNumber 是否只包含数字
     *
     * @return
     */
    public static String getRandom(int length, boolean onlyNumber) {
        SB sb = New.sb();
        int L = onlyNumber ? 10 : KEY_WORDS.length;
        if (onlyNumber) {
            for (int i = 0; i < length; ++i) {
                sb.append(RANDOM.nextInt(L));
            }
        } else {
            for (int i = 0; i < length; ++i) {
                sb.append(KEY_WORDS[RANDOM.nextInt(L)]);
            }
        }
        return sb.get();
    }

    /**
     * 判断是否是本地IP
     *
     * @param ip          需要判断的IP
     * @param removeSpace 是否移除所有空格
     *
     * @return
     */
    public static boolean isLocalhost(String ip, boolean removeSpace) {
        ip = removeSpace ? removeSpaces(ip) : ip;
        if (!isNull(ip)) {
            return ip.replaceAll("[0\\.]", NULL).length() == 0 ||
                    "127.0.0.1".equals(ip) ||
                    "localhost".equals(ip);
        }
        return true;
    }

    /**
     * 是否URL格式
     *
     * @param check 需检验的字符串
     *
     * @return
     */
    public static boolean isURLFormat(String check) {
        return match(check, Regex.URL);
    }

    /**
     * 是否邮箱格式
     *
     * @param check
     *
     * @return
     */
    public static boolean isEmailFormat(String check) {
        return match(check, Regex.EMAIL);
    }

    /**
     * 是否英文
     *
     * @param check
     *
     * @return
     */
    public static boolean isOnlyEnglish(String check) {
        return match(check, Regex.ONLY_ENGLISH);
    }

    /**
     * 是否整数
     *
     * @param check
     *
     * @return
     */
    public static boolean isLong(String check) {
        return match(check, Regex.LONG);
    }

    /**
     * 是否数字，包括小数
     *
     * @param check
     *
     * @return
     */
    public static boolean isNumeric(String check) {
        return match(check, Regex.NUMERIC);
    }

    /**
     * 是否无符号数
     *
     * @param check
     *
     * @return
     */
    public static boolean isUnsignedNumeric(String check) {
        return match(check, Regex.UNSIGNED_NUMERIC);
    }

    /**
     * 是否浮点数，包括float，不包括整数
     *
     * @param check
     *
     * @return
     */
    public static boolean isDouble(String check) {
        return match(check, Regex.DOUBLE) && !isLong(check);
    }

    /**
     * 是否时间
     *
     * @param check
     *
     * @return
     */
    public static boolean isTime(String check) {
        return match(check, Regex.TIME);
    }

    /**
     * 是否日期
     *
     * @param check
     *
     * @return
     */
    public static boolean isDate(String check) {
        return match(check, Regex.DATE);
    }

    /**
     * 是否日期时间
     *
     * @param check
     *
     * @return
     */
    public static boolean isDateTime(String check) {
        return match(check, Regex.DATE_TIME);
    }

    public static boolean isDateOrTime(String check) {
        return match(check, Regex.DATE_OR_TIME);
    }

    /**
     * 是否只包含中文，不能包含数字、标点
     *
     * @param check
     *
     * @return
     */
    public static boolean isOnlyChinese(String check) {
        return match(check, Regex.ONLY_CHINESE);
    }

    /**
     * 是否存在中文
     *
     * @param check
     *
     * @return
     */
    public static boolean isExistChinese(String check) {
        return match(check, Regex.CHINESE);
    }

    // ===================== 私有辅助方法 =====================

    /**
     * 正则表达式匹配
     *
     * @param check 需要检验的字符串
     * @param regex 正则表达式
     *
     * @return
     */
    private static boolean match(String check, String regex) {
        if (!isNull(check)) {
            String fix = check.trim();
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(fix);
            return m.find();
        } else {
            return false;
        }
    }

    /**
     * 修正16进制字符串长度，如：A --> 0A
     *
     * @param hex 需修正的16进制字符串
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:34
     */
    private static String fix(String hex) {

        return hex.length() % 2 == 0 ? hex : "0" + hex;
    }

    /**
     * 将修正后的16进制字符串 --> byte[]
     *
     * @param hex 修正后的16进制字符串
     *
     * @return byte[]
     *
     * @author AD
     * @date 2018-12-22 18:35
     */
    private static byte[] getBytes(String hex) {

        final char[] cs = toUpperArray(hex);
        byte[] bs = new byte[cs.length / 2];

        for (int i = 0, k = 0, L = bs.length; i < L; ++i, k += 2) {
            bs[i] = (byte) (HEX.indexOf(cs[k]) << 4 | HEX.indexOf(cs[k + 1]));
        }
        return bs;
    }

    /**
     * 移除字符串的所有空格
     *
     * @param fix 需修正的字符串
     *
     * @return java.lang.String
     *
     * @author AD
     * @date 2018-12-22 18:35
     */
    private static String removeSpaces(String fix) {

        final SB sb = New.sb();
        for (char c : fix.toCharArray()) {
            if (!Character.isSpaceChar(c)) sb.append(c);
        }
        return sb.get();
    }

    /**
     * 判断是否只包含16进制字符串
     *
     * @param check 需检查字符串
     *
     * @return boolean
     *
     * @author AD
     * @date 2018-12-22 18:37
     */
    private static boolean onlyHex(String check) {

        for (char c : check.toCharArray()) {
            if (ALL_HEX.indexOf(c) == -1) return false;
        }
        return true;
    }

    /**
     * 将字符串转为大写字符数组
     *
     * @param character 英文字符串
     *
     * @return char[]
     *
     * @author AD
     * @date 2018-12-22 18:36
     */
    private static char[] toUpperArray(String character) {

        return character.toUpperCase().toCharArray();
    }

}