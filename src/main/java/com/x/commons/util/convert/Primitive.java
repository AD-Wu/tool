package com.x.commons.util.convert;

import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;

import java.util.Map;

/**
 * @Date 2019-01-02 22:38
 * @Author AD
 */
public enum Primitive {

    BYTE(byte.class, Byte.class, 1) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toByte(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toByte(s);
        }
    },

    SHORT(short.class, Short.class, 2) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toShort(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toShort(s);
        }
    },

    INT(int.class, Integer.class, 4) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toInt(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toInt(s);
        }
    },

    LONG(long.class, Long.class, 8) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toLong(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toLong(s);
        }
    },

    FLOAT(float.class, Float.class, 4) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toFloat(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toFloat(s);
        }
    },

    DOUBLE(double.class, Double.class, 8) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toDouble(bs);
        }

        @Override
        public Object decode(final String s) {
            return Strings.toDouble(s);
        }
    },

    BOOLEAN(boolean.class, Boolean.class, 1) {
        @Override
        public Object decode(final byte[] bs) {
            return Converts.toBoolean(bs);
        }

        @Override
        public Object decode(final String s) {
            return !Strings.isNull(s);
        }
    },

    CHAR(char.class, Character.class, 2) {
        @Override
        public Object decode(final byte[] bs) {
            return ' ';
        }

        @Override
        public Object decode(final String s) {
            return s.toCharArray();
        }
    };

    public static int getLength(Class<?> primitiveClass) {
        return LENGTH_MAP.get(primitiveClass) == null ? 0 : LENGTH_MAP.get(primitiveClass);
    }

    public int getLength() {
        return this.byteCount;
    }

    public static Primitive of(Class<?> primitiveClass) {
        return SELF_MAP.get(primitiveClass);
    }

    /**
     * 基本数据类型
     */
    private final Class<?> clazz;
    /**
     * 基本数据类型对应的对象型
     */
    private final Class<?> objClazz;
    /**
     * 基本数据类型的字节数
     */
    private final int byteCount;
    private static final Map<Class, Integer> LENGTH_MAP = New.map(8);
    private static final Map<Class, Primitive> SELF_MAP = New.map(8);

    private Primitive(Class<?> clazz, Class<?> objClazz, int length) {
        this.clazz = clazz;
        this.objClazz = objClazz;
        this.byteCount = length;
    }

    static {
        init();
    }

    private static void init() {
        for (Primitive p : values()) {
            LENGTH_MAP.put(p.clazz, p.byteCount);
            SELF_MAP.put(p.clazz, p);
            SELF_MAP.put(p.objClazz, p);
        }
    }

    public abstract Object decode(byte[] bs);

    public abstract Object decode(String s);

}
