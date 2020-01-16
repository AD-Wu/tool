package com.x.framework.caching.datas;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.date.DateTimes;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.methods.MethodInfo;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/15 16:44
 */
public final class CacheHelper {

    private CacheHelper() {}

    public static <T> T[] getPageData(Class<T> clazz, T[] src, int var2, int length) {
        if (!XArrays.isEmpty(src)) {
            if (var2 <= 0) {
                var2 = 1;
            }
            if (length <= 0) {
                length = 1;
            }
            int srcPos = (var2 - 1) * length;
            if (srcPos < src.length) {
                if (srcPos + length > src.length) {
                    length = src.length - srcPos;
                }
                if (length < 1) {
                    return (T[]) Array.newInstance(clazz, 0);
                } else {
                    T[] result = (T[]) Array.newInstance(clazz, length);
                    System.arraycopy(src, srcPos, result, 0, length);
                    return result;
                }
            } else {
                return (T[]) Array.newInstance(clazz, 0);
            }
        } else {
            return src;
        }
    }

    public static void upperCaseWhereKeys(Where[] wheres) {
        Arrays.stream(wheres).forEach(where -> {
            where.setK(where.getK().toUpperCase());
        });
    }

    public static String getWhereString(Where[] ws) {
        if (!XArrays.isEmpty(ws)) {
            Where[] wheres = new Where[ws.length];
            System.arraycopy(ws, 0, wheres, 0, ws.length);
            Arrays.sort(wheres, new Comparator<Where>() {
                public int compare(Where w1, Where w2) {
                    if (w1 != null && w2 != null) {
                        String key1 = w1.getK();
                        String key2 = w2.getK();
                        if (key1 == null && key2 == null) {
                            return 0;
                        } else if (key1 == null && key2 != null) {
                            return -1;
                        } else {
                            return key1 != null && key2 == null ? 1 : key1.compareToIgnoreCase(
                                    key2);
                        }
                    } else {
                        return 0;
                    }
                }
            });
            SB sb = New.sb();
            for (int i = 0, L = wheres.length; i < L; ++i) {
                Where where = wheres[i];
                if (where == null) {
                    return null;
                }
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(where.getK());
                sb.append(where.getO().toUpperCase());
                Object value = where.getV();
                if (value != null) {
                    if (value instanceof Date) {
                        sb.append(((Date) value).getTime());
                    } else if (value instanceof LocalDateTime) {
                        Date date = DateTimes.toDate((LocalDateTime) value);
                        sb.append(date.getTime());
                    } else {
                        sb.append(value);
                    }
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static int getHistoryCacheKey(String var0, Where[] wheres, KeyValue[] kvs) {
        if (!Strings.isNull(var0)) {
            SB sb = New.sb(var0);
            Object value;
            if (!XArrays.isEmpty(wheres)) {
                Where[] copyWheres = new Where[wheres.length];
                System.arraycopy(wheres, 0, copyWheres, 0, wheres.length);
                Arrays.sort(copyWheres, new Comparator<Where>() {
                    public int compare(Where where1, Where where2) {
                        if (where1 != null && where2 != null) {
                            String k1 = where1.getK();
                            String k2 = where2.getK();
                            if (k1 == null && k2 == null) {
                                return 0;
                            } else if (k1 == null && k2 != null) {
                                return -1;
                            } else {
                                return k1 != null && k2 == null ? 1 : k1.compareToIgnoreCase(
                                        k2);
                            }
                        } else {
                            return 0;
                        }
                    }
                });

                for (int i = 0, L = copyWheres.length; i < L; ++i) {
                    Where where = copyWheres[i];
                    if (where != null) {
                        sb.append("$");
                        sb.append(where.getK());
                        sb.append(where.getO());
                        value = where.getV();
                        if (value != null) {
                            if (value instanceof Date) {
                                sb.append(((Date) value).getTime());
                            } else {
                                sb.append(value);
                            }
                        }
                    }
                }
            }

            if (!XArrays.isEmpty(kvs)) {
                for (int i = 0, L = kvs.length; i < L; ++i) {
                    KeyValue kv = kvs[i];
                    if (kv != null) {
                        String key = kv.getK();
                        if (key != null && key.length() != 0) {
                            sb.append("$");
                            sb.append(key);
                            sb.append(":");
                            value = kv.getV();
                            if (value != null && "DESC".equals(value.toString().toUpperCase())) {
                                sb.append("DESC");
                            } else {
                                sb.append("ASC");
                            }
                        }
                    }
                }
            }
            String result = sb.toString().toUpperCase();
            return result.hashCode();
        } else {
            return 0;
        }
    }

    public static String getPrimaryValueAsString(Object[] os) {
        if (os != null && os.length != 0) {
            SB sb = New.sb();
            for (int i = 0, L = os.length; i < L; ++i) {
                Object o = os[i];
                sb.append(o);
                sb.append("|");
            }
            return String.valueOf(sb.toString().hashCode());
        } else {
            return null;
        }
    }

    public static String[] upperCasePrimaryKeys(String[] pks) {
        String[] keys = new String[pks.length];

        for (int i = 0, L = pks.length; i < L; ++i) {
            keys[i] = pks[i].toUpperCase();
        }

        return keys;
    }

    public static String getPrimaryValueByWheres(String[] pks, Where[] wheres) {
        if (wheres.length != pks.length) {
            return null;
        } else {
            SB sb = New.sb();
            for (int i = 0, L = pks.length; i < L; ++i) {
                String pk = pks[i];
                boolean var7 = false;
                for (int k = 0, len = wheres.length; k < len; ++k) {
                    Where where = wheres[k];
                    if (pk.equals(where.getK())) {
                        if (!"=".equals(where.getO())) {
                            return null;
                        }
                        sb.append(where.getV());
                        sb.append("|");
                        var7 = true;
                        break;
                    }
                }

                if (!var7) {
                    return null;
                }
            }

            return String.valueOf(sb.toString().hashCode());
        }
    }

    public static String getPrimaryValueByKeys(Map<String, MethodInfo> getMethodInfos, String[] pks, Object o) throws Exception {
        if (o == null) {
            return null;
        } else {
            SB sb = New.sb();

            for (int i = 0, L = pks.length; i < L; ++i) {
                String pk = pks[i];
                MethodInfo info = getMethodInfos.get(pk);
                sb.append(info.getMethod().invoke(o));
                sb.append("|");
            }

            return String.valueOf(sb.toString().hashCode());
        }
    }

    public static ValueMatcher[] getWhereMatchers(Map<String, MethodInfo> getMethodInfos, Where[] wheres) throws Exception {
        ValueMatcher[] matchers = new ValueMatcher[wheres.length];

        for (int i = 0, L = wheres.length; i < L; ++i) {
            ValueMatcher matcher = ValueMatcher.getValueMatcher(getMethodInfos, wheres[i]);
            if (matcher == null) {
                throw new Exception("Where condition error: " + getWhereString(wheres));
            }
            matchers[i] = matcher;
        }

        return matchers;
    }

    public static boolean matchCondition(ValueMatcher[] matchers, Object o) throws Exception {

        for (int i = 0, L = matchers.length; i < L; ++i) {
            ValueMatcher matcher = matchers[i];
            if (!matcher.match(o)) {
                return false;
            }
        }

        return true;
    }

    public static void sortArray(Map<String, MethodInfo> getMethodInfos, KeyValue[] kvs, Object[] os) {
        if (kvs != null && kvs.length != 0 && os != null && os.length >= 2) {
            final ValueSorter[] sorters = new ValueSorter[kvs.length];

            for (int i = 0, L = kvs.length; i < L; ++i) {
                sorters[i] = ValueSorter.getValueSorter(getMethodInfos, kvs[i]);
            }

            Arrays.sort(os, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {

                    for (int i = 0, L = sorters.length; i < L; ++i) {
                        ValueSorter sorter = sorters[i];
                        if (sorter != null) {
                            if (sorter.matchAsc(o1, o2)) {
                                return -1;
                            }
                            if (sorter.matchDesc(o1, o2)) {
                                return 1;
                            }
                        }
                    }
                    return 0;
                }
            });
        }
    }

    public static ValueUpdater[] getUpdaters(Map<String, MethodInfo> var0, KeyValue[] kvs) throws Exception {
        ValueUpdater[] updaters = new ValueUpdater[kvs.length];

        for (int i = 0, L = kvs.length; i < L; ++i) {
            KeyValue kv = kvs[i];
            String K = kv.getK().toUpperCase();
            MethodInfo methodInfo = var0.get(K);
            Method method = methodInfo.getMethod();
            ValueUpdater updater = new ValueUpdater(K, method, fixValueType(method, kv.getV()));
            updaters[i] = updater;
        }

        return updaters;
    }

    private static Object fixValueType(Method method, Object converted) {
        Class[] paramTypes = method.getParameterTypes();
        if (XArrays.isEmpty(paramTypes)) {
            return converted;
        } else {
            Class<?> firstParamType = paramTypes[0];
            int type = ClassCode.getType(firstParamType);
            switch (type) {
                case ClassCode.BYTE:
                    return Converts.toByte(converted);
                case ClassCode.SHORT:
                    return Converts.toShort(converted);
                case ClassCode.INT:
                    return Converts.toInt(converted);
                case ClassCode.LONG:
                    return Converts.toLong(converted);
                case ClassCode.FLOAT:
                    return Converts.toFloat(converted);
                case ClassCode.DOUBLE:
                    return Converts.toDouble(converted);
                case ClassCode.BOOLEAN:
                    return Converts.toBoolean(converted);
                case ClassCode.STRING:
                    return String.valueOf(converted);
                case ClassCode.DATE:
                    return Converts.toDate(converted);
                case ClassCode.LOCAL_DATE_TIME:
                    return Converts.toLocalDateTime(converted);
                default:
                    return converted;

            }
        }
    }
}
