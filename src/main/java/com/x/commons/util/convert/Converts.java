package com.x.commons.util.convert;

import com.x.commons.util.string.Strings;
import lombok.NonNull;

/**
 * @Date 2018-12-18 22:49
 * @Author AD
 */

public final class Converts {

    // ====================== 构造方法 =======================

    private Converts() {}

    // ======================== API ========================

    public static boolean toBoolean(Object value) {
        if (value == null) {return false;}
        String t = Strings.toUppercase(value);
        return "TRUE".equals(t) || "Y".equals(t) || (!"0".equals(t));
    }

    public static boolean toBoolean(@NonNull byte[] bs) { return bs[0] == 0 ? false : true;}

    public static boolean toBoolean(@NonNull byte[] bs,int expect) { return expect == toInt(bs);}

    public static boolean toBoolean(@NonNull byte[] bs,String expect) { return Strings.toASCII(bs).equals(expect);}

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

    public static float toFloat(@NonNull byte[] bs) { return Digit.FloatConverter.FLOAT.toFloat(bs);}

    public static double toDouble(@NonNull byte[] bs) { return Digit.FloatConverter.DOUBLE.toDouble(bs);}

    public static byte[] toBytes(short value) { return Digit.SHORT.toBytes(value); }

    public static byte[] toBytes(int value) { return Digit.INT.toBytes(value); }

    public static byte[] toBytes(long value) { return Digit.LONG.toBytes(value); }

    public static byte[] toBytes(float value) { return Digit.FloatConverter.FLOAT.toBytes(value); }

    public static byte[] toBytes(double value) { return Digit.FloatConverter.DOUBLE.toBytes(value); }

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
            for (; k < bc; ++k,++move) {
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
                return bs.length <= 4 ? Float.intBitsToFloat(INT.toNumber(bs).intValue()) : (float) toDouble(bs);
            }

            private double toDouble(byte[] bs) {
                return bs.length <= 4 ? toFloat(bs) : Double.longBitsToDouble(LONG.toNumber(bs).longValue());
            }
        }

    }

}
