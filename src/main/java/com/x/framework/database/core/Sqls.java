package com.x.framework.database.core;

import com.x.commons.collection.KeyValue;
import com.x.commons.collection.Where;
import com.x.commons.database.pool.DatabaseType;
import com.x.commons.util.bean.New;
import com.x.commons.util.bean.SB;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.methods.MethodInfo;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;

/**
 * @Desc SQL工具类
 * @Date 2019-12-05 22:17
 * @Author AD
 */
public final class Sqls {
    // ------------------------ 变量定义 ------------------------
    
    // ------------------------ 构造方法 ------------------------
    
    private Sqls() {}
    
    // ------------------------ 方法定义 ------------------------
    
    /**
     * 获取返回值类型所对应的sql类型
     *
     * @param method
     *
     * @return
     */
    public static int getReturnSqlType(Method method) {
        return getSqlType(method.getReturnType());
    }
    
    /**
     * 获取方法第一个参数类型所对应的sql类型
     *
     * @param method
     *
     * @return
     */
    public static int getParameterSqlType(Method method) {
        Class[] classes = method.getParameterTypes();
        return classes.length == 1 ? getSqlType(classes[0]) : Types.NULL;
    }
    
    public static SQLParams getCreateParams(MethodInfo[] getInfos, Map<String, MethodInfo> setInfoMap, List<String> pks,
            Object data) {
        int L = getInfos.length;
        Object[] params = new Object[L];
        int[] sqlTypes = new int[L];
        String pk = pks.size() == 1 ? pks.get(0) : null;
        
        for (int i = 0; i < L; ++i) {
            MethodInfo getInfo = getInfos[i];
            DatabaseType dbType = getInfo.getDbType();
            int sqlType = getInfo.getSqlType();
            
            try {
                // get调用
                Object param = getInfo.getMethod().invoke(data);
                String PROP = getInfo.getKey();
                // 字符串类型
                if (sqlType == Types.VARCHAR) {
                    if ((param == null || "".equals(param)) && PROP.equals(pk)) {
                        param = Strings.UUID();
                        MethodInfo setInfo = setInfoMap.get(PROP);
                        if (setInfo != null) {
                            setInfo.getMethod().invoke(data, param);
                        }
                    }
                } else {
                    param = toSQLData(param, dbType, sqlType, getInfo.getMethod().getReturnType());
                }
                
                params[i] = param;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            sqlTypes[i] = sqlType;
        }
        return new SQLParams("", params, sqlTypes);
    }
    
    public static Object toJavaData(Object columnValue, MethodInfo setInfo) {
        int sqlType = setInfo.getSqlType();
        switch (sqlType) {
            case Types.TINYINT:
                return Converts.toByte(columnValue);
            case Types.BIGINT:
                return Converts.toLong(columnValue);
            case Types.CHAR:
                Class paramType = setInfo.getMethod().getParameterTypes()[0];
                if (!paramType.equals(Boolean.TYPE) && !paramType.equals(Boolean.class)) {
                    return columnValue == null ? null : String.valueOf(columnValue).trim();
                } else {
                    if (columnValue == null) {
                        return false;
                    }
                    String s = String.valueOf(columnValue);
                    return s.equals("Y");
                }
            case Types.NUMERIC:
                return Converts.toBigDecimal(columnValue);
            case Types.INTEGER:
                return Converts.toInt(columnValue);
            case Types.SMALLINT:
                return Converts.toShort(columnValue);
            case Types.FLOAT:
                return Converts.toFloat(columnValue);
            case Types.DOUBLE:
                return Converts.toDouble(columnValue);
            case Types.VARCHAR:
                return columnValue == null ? null : String.valueOf(columnValue).trim();
            case Types.TIMESTAMP:
                if (columnValue == null) {
                    return null;
                } else if (columnValue instanceof java.sql.Date) {
                    java.sql.Date sqlDate = (java.sql.Date) columnValue;
                    return new Date(sqlDate.getTime());
                } else if (columnValue instanceof Timestamp) {
                    Timestamp timestamp = (Timestamp) columnValue;
                    return new Date(timestamp.getTime());
                }
            default:
                return columnValue;
        }
    }
    
    public static SQLParams getWhereParams(Map<String, MethodInfo> methodInfos, Where[] wheres) {
        if (!XArrays.isEmpty(wheres)) {
            SB sb = New.sb();
            List<Object> paramList = new ArrayList();
            List<Integer> sqlTypeList = new ArrayList();
            int wheresLen = wheres.length;
            int index = 0;
            while (true) {
                if (index < wheresLen) {
                    Where where = wheres[index];
                    if (where == null) {
                        return null;
                    }
                    String K = where.getK();
                    if (!Strings.isNull(K)) {
                        String operation = fixOperation(where.getO());
                        if (Strings.isNull(operation)) {
                            return null;
                        }
                        Object V = where.getV();
                        // 用空白符分割
                        String[] keys = K.toUpperCase().split("\\s*,\\s*");
                        if (keys.length > 1) {
                            if (sb.length() == 0) {
                                sb.append("(");
                            } else {
                                sb.append(" AND (");
                            }
                            int whereCount = 0;
                            for (int i = 0, L = keys.length; i < L; ++i) {
                                String key = keys[i];
                                if (methodInfos.containsKey(key)) {
                                    if (whereCount > 0) {
                                        sb.append(" OR ");
                                    }
                                    analyzeWhere(key, operation, V, methodInfos, sb, paramList, sqlTypeList);
                                    ++whereCount;
                                }
                            }
                            if (whereCount == 0) {
                                return null;
                            }
                            sb.append(")");
                        } else {
                            if (sb.length() > 0) {
                                sb.append(" AND ");
                            }
                            if (!analyzeWhere(keys[0], operation, V, methodInfos, sb, paramList, sqlTypeList)) {
                                return null;
                            }
                        }
                        ++index;
                        continue;
                    }
                    return null;
                }
                int[] sqlTypes = new int[sqlTypeList.size()];
                wheresLen = 0;
                for (index = sqlTypeList.size(); wheresLen < index; ++wheresLen) {
                    sqlTypes[wheresLen] = (Integer) sqlTypeList.get(wheresLen);
                }
                return new SQLParams(" WHERE " + sb.toString(), paramList.toArray(new Object[paramList.size()]), sqlTypes);
            }
        } else {
            return new SQLParams("", null, null);
        }
    }
    
    public static String getOrderBy(Map<String, MethodInfo> methodInfoMap, KeyValue[] kvs) {
        if (!XArrays.isEmpty(kvs)) {
            SB sb = New.sb(" ORDER BY ");
            boolean failed = false;
            
            for (int i = 0, L = kvs.length; i < L; ++i) {
                KeyValue kv = kvs[i];
                String k = kv.getK();
                if (Strings.isNull(k)) {
                    return null;
                }
                
                String v = String.valueOf(kv.getV());
                if (!Strings.isNull(v)) {
                    v = v.toUpperCase();
                    if (!"DESC".equals(v) && !"ASC".equals(v)) {
                        return null;
                    }
                } else {
                    v = "ASC";
                }
                
                MethodInfo methodInfo = methodInfoMap.get(k.toUpperCase());
                if (methodInfo == null) {
                    return null;
                }
                if (i > 0) {
                    sb.append(",");
                }
                
                sb.append(methodInfo.getKey());
                sb.append(" ");
                sb.append(v);
                if (!failed) {
                    failed = true;
                }
            }
            return failed ? sb.toString() : "";
        } else {
            return "";
        }
    }
    
    public static SQLParams getUpdateParams(Map<String, MethodInfo> methodInfoMap, KeyValue[] kvs) {
        if (!XArrays.isEmpty(kvs)) {
            int L = kvs.length;
            SB sb = New.sb();
            Object[] params = new Object[L];
            int[] sqlTypes = new int[L];
            
            for(int i = 0; i < L; ++i) {
                KeyValue kv = kvs[i];
                String K = kv.getK();
                if (K == null || K.length() == 0) {
                    return null;
                }
                
                MethodInfo methodInfo = methodInfoMap.get(K.toUpperCase());
                if (methodInfo == null) {
                    return null;
                }
                
                K = methodInfo.getKey();
                int sqlType = methodInfo.getSqlType();
                Object param = toSQLData(kv.getV(), methodInfo.getDbType(), sqlType, methodInfo.getMethod().getReturnType());
                sqlTypes[i] = sqlType;
                params[i] = param;
                if (i == 0) {
                    sb.append(K);
                    sb.append("=?");
                } else {
                    sb.append(",");
                    sb.append(K);
                    sb.append("=?");
                }
            }
            return new SQLParams(sb.toString(), params, sqlTypes);
        } else {
            return null;
        }
    }
    
    public static SQLParams getPrimaryParamsByBean(Map<String, MethodInfo> methodInfoMap, List<String> pks, Object data) throws Exception {
        if (data == null) {
            return null;
        } else {
            int pksLength = pks.size();
            if (pksLength == 0) {
                return null;
            } else {
                Object[] params = new Object[pksLength];
                int[] sqlTypes = new int[pksLength];
                SB sb = New.sb(" WHERE ");
                
                for(int i = 0; i < pksLength; ++i) {
                    MethodInfo getInfo = methodInfoMap.get(pks.get(i));
                    if (getInfo == null) {
                        return null;
                    }
                    
                    params[i] = toSQLData(getInfo.getMethod().invoke(data), getInfo.getDbType(), getInfo.getSqlType(), getInfo.getMethod().getReturnType());
                    sqlTypes[i] = getInfo.getSqlType();
                    if (i == 0) {
                        sb.append(getInfo.getKey());
                        sb.append("=?");
                    } else {
                        sb.append(" AND ");
                        sb.append(getInfo.getKey());
                        sb.append("=?");
                    }
                }
                return new SQLParams(sb.toString(), params, sqlTypes);
            }
        }
    }
    
    public static SQLParams getPrimaryParams(Map<String, MethodInfo> methodInfoMap, List<String> pks, Object[] params) {
        int pksLength = pks.size();
        if (pksLength == 0) {
            return null;
        } else {
            Object[] sqlDatas = new Object[pksLength];
            int[] sqlTypes = new int[pksLength];
            
            for(int i = 0; i < pksLength; ++i) {
                MethodInfo methodInfo = methodInfoMap.get(pks.get(i));
                if (methodInfo == null) {
                    return null;
                }
                sqlDatas[i] = toSQLData(params[i], methodInfo.getDbType(), methodInfo.getSqlType(), methodInfo.getMethod().getReturnType());
                sqlTypes[i] = methodInfo.getSqlType();
            }
            
            return new SQLParams("", sqlDatas, sqlTypes);
        }
    }
    
    public static SQLParams getUpdateBeanParams(MethodInfo[] methodInfos, List<String> var1, Object var2) {
        if (var1 != null && var1.size() != 0) {
            int var3 = methodInfos.length;
            Object[] var4 = new Object[var3];
            int[] var5 = new int[var3];
            int var6 = 0;
            HashMap var7 = new HashMap();
            
            int var8;
            for(var8 = 0; var8 < var3; ++var8) {
                MethodInfo var9 = methodInfos[var8];
                if (var1.contains(var9.getKey())) {
                    var7.put(var9.getKey(), var9);
                } else {
                    try {
                        var4[var6] = toSQLData(var9.getMethod().invoke(var2), var9.getDbType(), var9.getSqlType(), var9.getMethod().getReturnType());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    var5[var6] = var9.getSqlType();
                    ++var6;
                }
            }
            
            var8 = 0;
            
            for(int var14 = var1.size(); var8 < var14; ++var8) {
                MethodInfo var10 = (MethodInfo)var7.get(var1.get(var8));
                
                try {
                    var4[var6] = toSQLData(var10.getMethod().invoke(var2), var10.getDbType(), var10.getSqlType(), var10.getMethod().getReturnType());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                
                var5[var6] = var10.getSqlType();
                ++var6;
            }
            return new SQLParams("", var4, var5);
        } else {
            return null;
        }
    }
    /**
     * 根据Java里的类型获取对应的SQL数据类型
     *
     * @param returnType
     *
     * @return
     */
    public static int getSqlType(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType.equals(Short.TYPE)) {
                return Types.SMALLINT;
            }
            
            if (returnType.equals(Character.TYPE)) {
                return Types.CHAR;
            }
            
            if (returnType.equals(Boolean.TYPE)) {
                return Types.CHAR;
            }
            
            if (returnType.equals(Float.TYPE)) {
                return Types.FLOAT;
            }
            
            if (returnType.equals(Double.TYPE)) {
                return Types.DOUBLE;
            }
            
            if (returnType.equals(Integer.TYPE)) {
                return Types.INTEGER;
            }
            
            if (returnType.equals(Long.TYPE)) {
                return Types.BIGINT;
            }
            
            if (returnType.equals(Byte.TYPE)) {
                return Types.TINYINT;
            }
        } else {
            if (returnType.equals(String.class)) {
                return Types.VARCHAR;
            }
            
            if (returnType.equals(Date.class)) {
                return Types.TIMESTAMP;
            }
            
            if (returnType.equals(BigDecimal.class)) {
                return Types.NUMERIC;
            }
            
            if (returnType.equals(Short.class)) {
                return Types.SMALLINT;
            }
            
            if (returnType.equals(Character.class)) {
                return Types.CHAR;
            }
            
            if (returnType.equals(Boolean.class)) {
                return Types.CHAR;
            }
            
            if (returnType.equals(Float.class)) {
                return Types.FLOAT;
            }
            
            if (returnType.equals(Double.class)) {
                return Types.DOUBLE;
            }
            
            if (returnType.equals(Integer.class)) {
                return Types.INTEGER;
            }
            
            if (returnType.equals(Long.class)) {
                return Types.BIGINT;
            }
            
            if (returnType.equals(Byte.class)) {
                return Types.TINYINT;
            }
        }
        return Types.OTHER;
    }
    
    // ------------------------ 私有方法 ------------------------
    private static Object toSQLData(Object param, DatabaseType dbType, int sqlType, Class<?> returnType) {
        if (sqlType == Types.CHAR && (returnType.equals(Boolean.TYPE) || returnType.equals(Boolean.class))) {
            return (Boolean) param ? "Y" : "N";
        } else {
            return param;
        }
    }
    
    private static boolean analyzeWhere(String PROP, String operation, Object value, Map<String, MethodInfo> methodInfoMap, SB sb,
            List<Object> params, List<Integer> sqlTypes) {
        MethodInfo info = methodInfoMap.get(PROP);
        if (info == null) {
            return false;
        } else {
            if (value == null || "null".equals(value)) {
                if ("=".equals(operation)) {
                    sb.append(PROP);
                    sb.append(" IS NULL");
                    return true;
                }
                
                if ("<>".equals(operation)) {
                    sb.append(PROP);
                    sb.append(" IS NOT NULL");
                    return true;
                }
            }
            
            int sqlType = info.getSqlType();
            Object param;
            if (sqlType == Types.VARCHAR) {
                if (" LIKE ".equals(operation)) {
                    PROP = "UPPER(" + PROP + ")";
                    param = String.valueOf(value).toUpperCase();
                } else {
                    param = String.valueOf(value);
                }
            } else {
                param = toSQLData(value, info.getDbType(), sqlType, info.getMethod().getReturnType());
            }
            
            sb.append(PROP);
            sb.append(operation);
            sb.append("?");
            params.add(param);
            sqlTypes.add(sqlType);
            return true;
        }
    }
    
    /**
     * 修正操作符
     *
     * @param operator
     *
     * @return
     */
    private static String fixOperation(String operator) {
        if (!Strings.isNull(operator)) {
            if (!operator.equals("=") && !operator.equals("<>") && !operator.equals(">") && !operator.equals("<") &&
                !operator.equals(">=") && !operator.equals("<=")) {
                return operator.trim().toLowerCase().equals("like") ? " LIKE " : null;
            } else {
                return operator;
            }
        } else {
            return null;
        }
    }
    
}
