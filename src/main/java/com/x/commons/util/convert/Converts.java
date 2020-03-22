package com.x.commons.util.convert;

import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.string.Strings;
import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Date 2018-12-18 22:49
 * @Author AD
 */

public final class Converts {
    
    // ====================== 构造方法 =======================
    
    private Converts() {}
    
    // ======================== API ========================
    
    /**
     * 将两个数组进行复制整合
     *
     * @param first
     * @param second
     * @param <T>
     *
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        } else {
            T[] ts = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, ts, first.length, second.length);
            return ts;
        }
        
    }
    
    public static int[] concat(int[] first, int[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        } else {
            int[] ts = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, ts, first.length, second.length);
            return ts;
        }
    }
    
    public static <T> T[] concatAll(T[] first, T[]... ts) {
        if (ts == null) {
            return first;
        }
        int firstLen = first == null ? 0 : first.length;
        int allLen = 0;
        allLen += firstLen;
        for (T[] t : ts) {
            if (t != null) {
                if (first == null) {
                    first = t;
                }
                allLen += t.length;
            }
        }
        if (allLen == 0) return null;
        T[] all = Arrays.copyOf(first, allLen);
        int L = first.length;
        for (T[] t : ts) {
            if (t != null && t.length > 0) {
                System.arraycopy(t, 0, all, L, t.length);
                L += t.length;
            }
            
        }
        return all;
    }
    
    public static float fix(float value, int decimalPlaces) {
        double pow = Math.pow(10.0D, (double) decimalPlaces);
        double floor = Math.floor((double) value * pow) / pow;
        return Double.valueOf(floor).floatValue();
    }
    
    public static double fix(double value, int decimalPlaces) {
        double pow = Math.pow(10.0D, (double) decimalPlaces);
        double floor = Math.floor(value * pow) / pow;
        return floor;
    }
    
    public static int toInt(Object value) {
        return toInt(value, 0, 10);
    }
    
    public static int toInt(Object value, int defaultValue) {
        return toInt(value, defaultValue, 10);
    }
    
    public static int toInt(Object value, int defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
            if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                String s = value.toString();
                if (Strings.isNull(s)) {
                    return defaultValue;
                } else {
                    try {
                        return Integer.parseInt(s, radix);
                    } catch (Exception e) {
                        return defaultValue;
                    }
                }
            } else {
                return ((Double) value).intValue();
            }
        } else {
            return (Integer) value;
        }
    }
    
    public static short toShort(Object value) {
        return toShort(value, (short) 0, 10);
    }
    
    public static short toShort(Object value, short defaultValue) {
        return toShort(value, defaultValue, 10);
    }
    
    public static short toShort(Object value, short defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Short.TYPE) && !(value instanceof Short)) {
            if (!value.getClass().equals(Integer.TYPE) && !(value instanceof Integer)) {
                if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
                    String s = value.toString();
                    if (Strings.isNull(s)) {
                        return defaultValue;
                    } else {
                        try {
                            return Short.valueOf(s, radix);
                        } catch (Exception e) {
                            return defaultValue;
                        }
                    }
                } else {
                    return ((Double) value).shortValue();
                }
            } else {
                return ((Integer) value).shortValue();
            }
        } else {
            return (Short) value;
        }
    }
    
    public static long toLong(Object value) {
        return toLong(value, 0L);
    }
    
    public static long toLong(Object value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Long.TYPE) && !(value instanceof Long)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    return Double.valueOf(s).longValue();
                } catch (Exception var5) {
                    return defaultValue;
                }
            }
        } else {
            return (Long) value;
        }
    }
    
    public static float toFloat(Object value) {
        return toFloat(value, 0.0F);
    }
    
    public static float toFloat(Object value, float defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Float.TYPE) && !(value instanceof Float)) {
            if (value instanceof Double) {
                return ((Double) value).floatValue();
            } else {
                String s = value.toString();
                if (Strings.isNull(s)) {
                    return defaultValue;
                } else {
                    try {
                        return Double.valueOf(s).floatValue();
                    } catch (Exception var4) {
                        return defaultValue;
                    }
                }
            }
        } else {
            return (Float) value;
        }
    }
    
    public static double toDouble(Object value) {
        return toDouble(value, 0.0D);
    }
    
    public static double toDouble(Object value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        } else if (!value.getClass().equals(Double.TYPE) && !(value instanceof Double)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    return Double.valueOf(s);
                } catch (Exception var5) {
                    return defaultValue;
                }
            }
        } else {
            return (Double) value;
        }
    }
    
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, (BigDecimal) null);
    }
    
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return value == null ? defaultValue : new BigDecimal(value.toString());
    }
    
    public static byte toByte(Object value) {
        return toByte(value, (byte) 0, 10);
    }
    
    public static byte toByte(Object value, byte defaultValue) {
        return toByte(value, defaultValue, 10);
    }
    
    public static byte toByte(Object value, byte defaultValue, int radix) {
        if (value == null) {
            return defaultValue;
        } else if (!(value instanceof Byte) && !(value instanceof Integer)) {
            String s = value.toString();
            if (Strings.isNull(s)) {
                return defaultValue;
            } else {
                try {
                    byte b = (byte) Integer.parseInt(s, radix);
                    return b;
                } catch (Exception e) {
                    return defaultValue;
                }
            }
        } else {
            return Byte.valueOf(value.toString());
        }
    }
    
    public static Date toDate(Object date) {
        return toDate(date, null);
    }
    
    public static Date toDate(Object date, Date defaultDate) {
        if (date == null) {
            return defaultDate;
        } else if (date instanceof Date) {
            return (Date) date;
        } else if (!(date instanceof Long) && !(date instanceof Integer)) {
            try {
                LocalDateTime parse = DateTimes.toLocalDateTime(String.valueOf(date));
                return parse == null ? defaultDate : DateTimes.toDate(parse);
            } catch (Exception e) {
                return defaultDate;
            }
        } else {
            return new Date((Long) date);
        }
    }
    
    public static Date toDate(String date, String pattern) {
        return toDate(date, pattern, null);
    }
    
    public static Date toDate(String date, String pattern, Date defaultDate) {
        try {
            LocalDateTime parse = DateTimes.parse(date, pattern);
            return DateTimes.toDate(parse);
        } catch (Exception e) {
            return defaultDate;
        }
    }
    
    public static LocalDateTime toLocalDateTime(Object date) {
        Date parse = toDate(date);
        return DateTimes.toLocalDateTime(parse);
    }
    
    public static <T> String toString(List<T> list) {
        return toString(list, ",");
    }
    
    public static <T> String toString(List<T> list, String split) {
        if (!XArrays.isEmpty(list)) {
            SB sb = New.sb();
            if (list != null) {
                for (int i = 0, L = list.size(); i < L; ++i) {
                    if (i > 0) {
                        sb.append(split);
                    }
                    sb.append(list.get(i));
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }
    
    public static boolean toBoolean(Object value) {
        if (value == null) {return false;}
        String t = Strings.toUppercase(value);
        return "TRUE".equals(t) || "Y".equals(t) || "YES".equals(t) || ("1".equals(t));
    }
    
    public static boolean toBoolean(Object value, boolean defaultValue) {
        if (value == null) {return defaultValue;}
        String t = Strings.toUppercase(value);
        return "TRUE".equals(t) || "Y".equals(t) || "YES".equals(t) || ("1".equals(t)) || defaultValue;
    }
    
    public static boolean toBoolean(@NonNull byte[] bs) {
        return bs[0] == 0 ? false : true;
    }
    
    public static boolean toBoolean(@NonNull byte[] bs, int expect) { return expect == toInt(bs);}
    
    public static boolean toBoolean(@NonNull byte[] bs, String expect) {
        return Strings.toASCII(bs).equals(expect);
    }
    
    public static byte toByte(@NonNull byte[] bs) {
        return Digit.BYTE.toNumber(bs).byteValue();
    }
    
    public static short toShort(@NonNull byte[] bs) {
        return Digit.SHORT.toNumber(bs).shortValue();
    }
    
    public static int toInt(@NonNull byte[] bs) {
        return Digit.INT.toNumber(bs).intValue();
    }
    
    public static long toLong(@NonNull byte[] bs) {
        return Digit.LONG.toNumber(bs).longValue();
    }
    
    public static float toFloat(@NonNull byte[] bs) {
        return Digit.FloatConverter.FLOAT.toFloat(bs);
    }
    
    public static double toDouble(@NonNull byte[] bs) {
        return Digit.FloatConverter.DOUBLE.toDouble(bs);
    }
    
    public static byte[] toBytes(byte value)  { return Digit.BYTE.toBytes(value); }
    
    public static byte[] toBytes(short value) { return Digit.SHORT.toBytes(value); }
    
    public static byte[] toBytes(int value)   { return Digit.INT.toBytes(value); }
    
    public static byte[] toBytes(long value)  { return Digit.LONG.toBytes(value); }
    
    public static byte[] toBytes(float value) { return Digit.FloatConverter.FLOAT.toBytes(value); }
    
    public static byte[] toBytes(double value) {
        return Digit.FloatConverter.DOUBLE.toBytes(value);
    }
    
    public static ByteBuffer toByteBuffer(String converted, String charset) {
        if (!Strings.isNull(converted)) {
            try {
                return ByteBuffer.wrap(converted.getBytes(charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    // ======================== Private ========================
    private enum Digit {
        BYTE(1),
        SHORT(2),
        INT(4),
        LONG(8);
        
        final int bc;
        
        Digit(int byteCount) { this.bc = byteCount; }
        
        private Number toNumber(byte[] bs) {
            long v = 0, L = bs.length > bc ? bc : bs.length, move = L;
            for (int i = 0; i < L; ++i) {
                v |= (long) (bs[i] & 0xFF) << (--move) * 8;
            }
            return v;
        }
        
        private byte[] toBytes(long value) {
            byte[] bs = new byte[bc];
            int pos = bc, k = 0;
            long move = 0, max = 0xFF;
            for (; k < bc; ++k, ++move) {
                bs[--pos] = (byte) ((value & (max << (8 * move))) >> (8 * move));
            }
            return bs;
        }
        
        private enum FloatConverter {
            FLOAT,
            DOUBLE;
            
            private byte[] toBytes(float value) {
                return INT.toBytes(Float.floatToIntBits((float) value));
            }
            
            private byte[] toBytes(double value) {
                return LONG.toBytes(Double.doubleToLongBits(value));
            }
            
            private float toFloat(byte[] bs) {
                return bs.length <= 4 ? Float.intBitsToFloat(
                        INT.toNumber(bs).intValue()) : (float) toDouble(bs);
            }
            
            private double toDouble(byte[] bs) {
                return bs.length <= 4 ? toFloat(bs) : Double.longBitsToDouble(
                        LONG.toNumber(bs).longValue());
            }
        }
        
    }
    
}
