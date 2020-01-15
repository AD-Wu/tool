package com.x.framework.caching.datas;

import com.ax.commons.collection.KeyValue;
import com.ax.commons.collection.Where;
import com.ax.commons.utils.ConvertHelper;
import com.ax.framework.caching.datas.ValueMatcher;
import com.ax.framework.caching.datas.ValueSorter;
import com.ax.framework.caching.datas.ValueUpdater;
import com.ax.framework.caching.methods.MethodInfo;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.date.DateTimes;

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

    public static <T> T[] getPageData(Class<T> clazz, T[] var1, int var2, int var3) {
        if (var1 != null && var1.length != 0) {
            if (var2 <= 0) {
                var2 = 1;
            }
            if (var3 <= 0) {
                var3 = 1;
            }
            int var4 = (var2 - 1) * var3;
            int var5 = var1.length;
            if (var4 < var5) {
                if (var4 + var3 > var1.length) {
                    var3 = var1.length - var4;
                }
                if (var3 < 1) {
                    return (T[]) Array.newInstance(clazz, 0);
                } else {
                    T[] var6 = (T[]) Array.newInstance(clazz, var3);
                    System.arraycopy(var1, var4, var6, 0, var3);
                    return var6;
                }
            } else {
                return (T[]) Array.newInstance(clazz, 0);
            }
        } else {
            return var1;
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

    public static int getHistoryCacheKey(String var0, Where[] var1, KeyValue[] var2) {
        if (var0 != null && var0.length() != 0) {
            StringBuilder var3 = new StringBuilder(var0);
            int var6;
            Object var9;
            if (var1 != null && var1.length > 0) {
                Where[] var4 = new Where[var1.length];
                System.arraycopy(var1, 0, var4, 0, var1.length);
                Arrays.sort(var4, new Comparator<Where>() {
                    public int compare(Where var1, Where var2) {
                        if (var1 != null && var2 != null) {
                            String var3 = var1.getK();
                            String var4 = var2.getK();
                            if (var3 == null && var4 == null) {
                                return 0;
                            } else if (var3 == null && var4 != null) {
                                return -1;
                            } else {
                                return var3 != null && var4 == null ? 1 : var3.compareToIgnoreCase(
                                        var4);
                            }
                        } else {
                            return 0;
                        }
                    }
                });
                Where[] var5 = var4;
                var6 = var4.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    Where var8 = var5[var7];
                    if (var8 != null) {
                        var3.append("$");
                        var3.append(var8.getK());
                        var3.append(var8.getO());
                        var9 = var8.getV();
                        if (var9 != null) {
                            if (var9 instanceof Date) {
                                var3.append(((Date) var9).getTime());
                            } else {
                                var3.append(var9);
                            }
                        }
                    }
                }
            }

            if (var2 != null && var2.length > 0) {
                KeyValue[] var10 = var2;
                int var12 = var2.length;

                for (var6 = 0; var6 < var12; ++var6) {
                    KeyValue var13 = var10[var6];
                    if (var13 != null) {
                        String var14 = var13.getK();
                        if (var14 != null && var14.length() != 0) {
                            var3.append("$");
                            var3.append(var14);
                            var3.append(":");
                            var9 = var13.getV();
                            if (var9 != null && "DESC".equals(var9.toString().toUpperCase())) {
                                var3.append("DESC");
                            } else {
                                var3.append("ASC");
                            }
                        }
                    }
                }
            }

            String var11 = var3.toString().toUpperCase();
            return var11.hashCode();
        } else {
            return 0;
        }
    }

    public static String getPrimaryValueAsString(Object[] var0) {
        if (var0 != null && var0.length != 0) {
            StringBuilder var1 = new StringBuilder();
            Object[] var2 = var0;
            int var3 = var0.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Object var5 = var2[var4];
                var1.append(var5);
                var1.append("|");
            }

            return String.valueOf(var1.toString().hashCode());
        } else {
            return null;
        }
    }

    public static String[] upperCasePromaryKeys(String[] var0) {
        String[] var1 = new String[var0.length];
        int var2 = 0;

        for (int var3 = var0.length; var2 < var3; ++var2) {
            var1[var2] = var0[var2].toUpperCase();
        }

        return var1;
    }

    public static String getPrimaryValueByWheres(String[] var0, Where[] var1) {
        if (var1.length != var0.length) {
            return null;
        } else {
            StringBuilder var2 = new StringBuilder();
            String[] var3 = var0;
            int var4 = var0.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String var6 = var3[var5];
                boolean var7 = false;
                Where[] var8 = var1;
                int var9 = var1.length;

                for (int var10 = 0; var10 < var9; ++var10) {
                    Where var11 = var8[var10];
                    if (var6.equals(var11.getK())) {
                        if (!"=".equals(var11.getO())) {
                            return null;
                        }

                        var2.append(var11.getV());
                        var2.append("|");
                        var7 = true;
                        break;
                    }
                }

                if (!var7) {
                    return null;
                }
            }

            return String.valueOf(var2.toString().hashCode());
        }
    }

    public static String getPrimaryValueByKeys(Map<String, MethodInfo> var0, String[] var1, Object var2) throws Exception {
        if (var2 == null) {
            return null;
        } else {
            StringBuilder var3 = new StringBuilder();
            String[] var4 = var1;
            int var5 = var1.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String var7 = var4[var6];
                MethodInfo var8 = (MethodInfo) var0.get(var7);
                var3.append(var8.getMethod().invoke(var2));
                var3.append("|");
            }

            return String.valueOf(var3.toString().hashCode());
        }
    }

    public static ValueMatcher[] getWhereMatchers(Map<String, MethodInfo> var0, Where[] var1) throws Exception {
        ValueMatcher[] var2 = new ValueMatcher[var1.length];
        int var3 = 0;

        for (int var4 = var1.length; var3 < var4; ++var3) {
            ValueMatcher var5 = ValueMatcher.getValueMatcher(var0, var1[var3]);
            if (var5 == null) {
                throw new Exception("Where condition error: " + getWhereString(var1));
            }

            var2[var3] = var5;
        }

        return var2;
    }

    public static boolean matchCondition(ValueMatcher[] var0, Object var1) throws Exception {
        ValueMatcher[] var2 = var0;
        int var3 = var0.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            ValueMatcher var5 = var2[var4];
            if (!var5.match(var1)) {
                return false;
            }
        }

        return true;
    }

    public static void sortArray(Map<String, MethodInfo> var0, KeyValue[] var1, Object[] var2) {
        if (var1 != null && var1.length != 0 && var2 != null && var2.length >= 2) {
            int var3 = var1.length;
            final com.ax.framework.caching.datas.ValueSorter[] var4 = new com.ax.framework.caching.datas.ValueSorter[var3];

            for (int var5 = 0; var5 < var3; ++var5) {
                var4[var5] = com.ax.framework.caching.datas.ValueSorter.getValueSorter(var0,
                                                                                       var1[var5]);
            }

            Arrays.sort(var2, new Comparator<Object>() {
                public int compare(Object var1, Object var2) {
                    com.ax.framework.caching.datas.ValueSorter[] var3 = var4;
                    int var4x = var3.length;

                    for (int var5 = 0; var5 < var4x; ++var5) {
                        ValueSorter var6 = var3[var5];
                        if (var6 != null) {
                            if (var6.matchAsc(var1, var2)) {
                                return -1;
                            }

                            if (var6.matchDesc(var1, var2)) {
                                return 1;
                            }
                        }
                    }

                    return 0;
                }
            });
        }
    }

    public static com.ax.framework.caching.datas.ValueUpdater[] getUpdaters(Map<String, MethodInfo> var0, KeyValue[] var1) throws Exception {
        com.ax.framework.caching.datas.ValueUpdater[] var2 = new com.ax.framework.caching.datas.ValueUpdater[var1.length];
        int var3 = 0;

        for (int var4 = var1.length; var3 < var4; ++var3) {
            KeyValue var5 = var1[var3];
            String var6 = var5.getK().toUpperCase();
            MethodInfo var7 = (MethodInfo) var0.get(var6);
            Method var8 = var7.getMethod();
            com.ax.framework.caching.datas.ValueUpdater var9 = new ValueUpdater(var6, var8,
                                                                                fixValueType(var8,
                                                                                             var5.getV()));
            var2[var3] = var9;
        }

        return var2;
    }

    private static Object fixValueType(Method var0, Object var1) {
        Class[] var2 = var0.getParameterTypes();
        if (var2.length == 0) {
            return var1;
        } else {
            Class var3 = var2[0];
            if (var3.equals(String.class)) {
                if (var1 != null) {
                    return var1.toString();
                }
            } else {
                if (var3.equals(Integer.TYPE) || var3.equals(Integer.class)) {
                    return ConvertHelper.toInt(var1);
                }

                if (var3.equals(Short.TYPE) || var3.equals(Short.class)) {
                    return ConvertHelper.toShort(var1);
                }

                if (var3.equals(Boolean.TYPE) || var3.equals(Boolean.class)) {
                    return ConvertHelper.toBoolean(var1);
                }

                if (var3.equals(Long.TYPE) || var3.equals(Long.class)) {
                    return ConvertHelper.toLong(var1);
                }

                if (var3.equals(Float.TYPE) || var3.equals(Float.class)) {
                    return ConvertHelper.toFloat(var1);
                }

                if (var3.equals(Double.TYPE) || var3.equals(Double.class)) {
                    return ConvertHelper.toDouble(var1);
                }

                if (var3.equals(Date.class)) {
                    if (var1 != null) {
                        return ConvertHelper.toDate(var1);
                    }
                } else if (var3.equals(Byte.TYPE) || var3.equals(Byte.class)) {
                    return ConvertHelper.toByte(var1);
                }
            }

            return var1;
        }
    }
}
