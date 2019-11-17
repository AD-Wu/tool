package com.x.commons.decoder.core;

import com.x.commons.util.bean.New;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.convert.Strings;
import lombok.NonNull;

import java.util.Map;

/**
 * @Date 2019-01-02 22:38
 * @Author AD
 */
public enum Primitive {

    BYTE(byte.class, 1) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toByte(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toByte(s);}
    },

    SHORT(short.class, 2) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toShort(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toShort(s);}
    },

    INT(int.class, 4) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toInt(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toInt(s);}
    },

    LONG(long.class, 8) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toLong(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toLong(s);}
    },

    FLOAT(float.class, 4) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toFloat(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toFloat(s);}
    },

    DOUBLE(double.class, 8) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toDouble(bs);
        }
        @Override
        public Object decode(final String s) {return Strings.toDouble(s);}
    },

    BOOLEAN(boolean.class, 1) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return Converts.toBoolean(bs);
        }
        @Override
        public Object decode(final String s) {return !Strings.isNull(s);}
    },

    CHAR(char.class, 2) {
        @Override
        public Object decode(@NonNull final byte[] bs) {
            return ' ';
        }
        @Override
        public Object decode(final String s) {return s.toCharArray();}
    };

    public static int getLength(@NonNull Class<?> primitiveClass) {
        return LENGTH_MAP.get(primitiveClass) == null ? 0 : LENGTH_MAP.get(primitiveClass);
    }

    public int getLength() {
        return this.byteCount;
    }

    public static Primitive of(@NonNull Class<?> primitiveClass) {
        return SELF_MAP.get(primitiveClass);
    }

    private final Class<?> clazz;
    private final int byteCount;
    private static final Map<Class, Integer> LENGTH_MAP = New.map(8);
    private static final Map<Class, Primitive> SELF_MAP = New.map(8);

    private Primitive(Class<?> clazz, int length) {
        this.clazz = clazz;
        this.byteCount = length;
    }

    static {
        init();
    }

    private static void init() {
        for (Primitive p : values()) {
            LENGTH_MAP.put(p.clazz, p.byteCount);
            SELF_MAP.put(p.clazz, p);
        }
    }

    public abstract Object decode(@NonNull byte[] bs);

    public abstract Object decode(String s);

}
