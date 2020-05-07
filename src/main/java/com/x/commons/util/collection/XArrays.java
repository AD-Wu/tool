package com.x.commons.util.collection;

import com.x.commons.util.bean.New;
import com.x.commons.util.convert.Primitive;
import com.x.commons.util.reflact.Clazzs;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Date 2019-03-04 21:52
 * @Author AD
 */
public final class XArrays {

    /**
     * 空 byte[] 数组
     */
    public static final byte[] EMPTY_BYTE = new byte[0];

    /**
     * 空 short[] 数组
     */
    public static final short[] EMPTY_SHORT = new short[0];

    /**
     * 空 int[] 数组
     */
    public static final int[] EMPTY_INT = new int[0];

    /**
     * 空 long[] 数组
     */
    public static final long[] EMPTY_LONG = new long[0];

    /**
     * 空 float[] 数组
     */
    public static final float[] EMPTY_FLOAT = new float[0];

    /**
     * 空 double[] 数组
     */
    public static final double[] EMPTY_DOUBLE = new double[0];

    /**
     * 空 String[] 数组
     */
    public static final String[] EMPTY_STRING = new String[0];

    /**
     * 空 File[] 数组
     */
    public static final File[] EMPTY_FILE = new File[0];

    /**
     * 空 Object[] 数组
     */
    public static final Object[] EMPTY = new Object[0];

    private XArrays() {
    }

    public static byte[] copy(byte[] data) {
        if (!isEmpty(data)) {
            byte[] copy = new byte[data.length];
            System.arraycopy(data, 0, copy, 0, copy.length);
            return copy;
        }
        return EMPTY_BYTE;
    }

    public static boolean isValid(Object[] os1, Object[] os2) {
        if (!isEmpty(os1) && !isEmpty(os2) && os1.length == os2.length) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(Set<T> set) {
        return set == null || set.size() == 0;
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(int[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(byte[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(short[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(long[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(float[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(double[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static boolean isEmpty(char[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    public static <T> Set<T> toSet(T[] ts) {
        return Stream.of(ts).collect(Collectors.toSet());
    }

    public static <T> T[] toArray(List<T> list, Class<T> clazz) {
        if (isEmpty(list)) {
            return null;
        }
        T[] ts = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(ts);
    }

    public static <T> List<T> join(T[] arr1, T[] arr2) {
        if (arr1 == null) {
            return Arrays.asList(arr2);
        }
        if (arr2 == null) {
            return Arrays.asList(arr1);
        }
        List<T> list = New.list(arr1.length + arr2.length);
        list.addAll(Arrays.asList(arr1));
        list.addAll(Arrays.asList(arr2));
        return list;
    }

    /**
     * 对象型数组和基本数据类型数组进行互转，如：byte[] <-> Byte[]
     *
     * @param value
     * @param targetClass 目标类型,可选参数，为null时自动互换，如：byte[] <-> Byte[]；
     * @return
     */
    public static Object convert(Object value, Class<?> targetClass) {
        // 如果目标类型为空，就自动转换
        if (targetClass==null) {
            return convert(value);
        }
        // // 获取第一个值
        // Class<?> targetClass = targetClasses[0];
        // 如果不是数组，返回原值
        if (!Clazzs.isArray(targetClass)) {
            return value;
        }
        // 如果value的组成类型=targetClass的组成类型，返回原值
        if (targetClass.getComponentType() == value.getClass().getComponentType()) {
            return value;
        }
        return convert(value);
    }

    /**
     * 对象型数组和基本数据类型数组进行互转，如：byte[] <-> Byte[]
     *
     * @param value 数组（基本数据类型或所对应的对象类型）
     * @return
     */
    private static Object convert(Object value) {
        if (value == null) {
            return null;
        }
        if (!Clazzs.isArray(value.getClass())) {
            return value;
        }
        Class<?> clazz = value.getClass().getComponentType();
        Primitive pri = Primitive.of(clazz);
        if (pri == null) {
            return value;
        }
        Object instance = null;
        int L;
        if (Clazzs.isPrimitive(clazz)) {
            switch (pri) {
                case BYTE:
                    byte[] bs = (byte[]) value;
                    L = bs.length;
                    instance = Array.newInstance(Byte.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, bs[i]);
                    }
                    break;
                case SHORT:
                    short[] ss = (short[]) value;
                    L = ss.length;
                    instance = Array.newInstance(Short.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ss[i]);
                    }
                    break;
                case INT:
                    int[] is = (int[]) value;
                    L = is.length;
                    instance = Array.newInstance(Integer.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, is[i]);
                    }
                    break;
                case LONG:
                    long[] ls = (long[]) value;
                    L = ls.length;
                    instance = Array.newInstance(Long.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ls[i]);
                    }
                    break;
                case FLOAT:
                    float[] fs = (float[]) value;
                    L = fs.length;
                    instance = Array.newInstance(Float.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, fs[i]);
                    }
                    break;
                case DOUBLE:
                    double[] ds = (double[]) value;
                    L = ds.length;
                    instance = Array.newInstance(Double.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ds[i]);
                    }
                    break;
                case BOOLEAN:
                    boolean[] bools = (boolean[]) value;
                    L = bools.length;
                    instance = Array.newInstance(Boolean.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, bools[i]);
                    }
                    break;
                case CHAR:
                    char[] cs = (char[]) value;
                    L = cs.length;
                    instance = Array.newInstance(Character.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, cs[i]);
                    }
                    break;
                default:
                    return value;
            }
            return instance;
        } else {
            switch (pri) {
                case BYTE:
                    Byte[] bs = (Byte[]) value;
                    L = bs.length;
                    instance = Array.newInstance(byte.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, bs[i]);
                    }
                    break;
                case SHORT:
                    Short[] ss = (Short[]) value;
                    L = ss.length;
                    instance = Array.newInstance(short.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ss[i]);
                    }
                    break;
                case INT:
                    Integer[] is = (Integer[]) value;
                    L = is.length;
                    instance = Array.newInstance(int.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, is[i]);
                    }
                    break;
                case LONG:
                    Long[] ls = (Long[]) value;
                    L = ls.length;
                    instance = Array.newInstance(long.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ls[i]);
                    }
                    break;
                case FLOAT:
                    Float[] fs = (Float[]) value;
                    L = fs.length;
                    instance = Array.newInstance(float.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, fs[i]);
                    }
                    break;
                case DOUBLE:
                    Double[] ds = (Double[]) value;
                    L = ds.length;
                    instance = Array.newInstance(double.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, ds[i]);
                    }
                    break;
                case BOOLEAN:
                    Boolean[] bools = (Boolean[]) value;
                    L = bools.length;
                    instance = Array.newInstance(boolean.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, bools[i]);
                    }
                    break;
                case CHAR:
                    Character[] cs = (Character[]) value;
                    L = cs.length;
                    instance = Array.newInstance(char.class, L);
                    for (int i = 0; i < L; i++) {
                        Array.set(instance, i, cs[i]);
                    }
                    break;
                default:
                    return value;
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        int[] is = {1, 2, 3};

        System.out.println("is=" + is.getClass());
        Object convert = convert(is);
        System.out.println("is after convert=" + convert.getClass());

        Integer[] ints = new Integer[]{1, 2, 3};
        System.out.println("ints=" + ints.getClass());
        Object convert1 = convert(ints);
        System.out.println("ints after convert=" + convert1.getClass());
        boolean empty = isEmpty(New.list());
        System.out.println(empty);

    }

}
