package com.x.framework.caching.datas;

import com.x.commons.collection.Where;
import com.x.commons.parser.Parsers;
import com.x.commons.parser.core.IParser;
import com.x.commons.util.bean.New;
import com.x.commons.util.string.Strings;
import com.x.framework.caching.datas.matchers.Compares;
import com.x.framework.caching.datas.matchers.IComparer;
import com.x.framework.caching.datas.matchers.LikeComparer;
import com.x.framework.caching.methods.MethodInfo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/16 11:26
 */
public class ValueMatcher {

    private Method[] methods;

    private IComparer comparer;

    private Object conditionValue;

    public ValueMatcher(Method[] methods, IComparer comparer, Object conditionValue) {
        this.methods = methods;
        this.comparer = comparer;
        this.conditionValue = conditionValue;
    }

    public boolean match(Object o) throws Exception {
        if (methods.length > 1) {
            for (int i = 0, L = methods.length; i < L; ++i) {
                Method method = methods[i];
                if (comparer.compare(method.invoke(o), conditionValue)) {
                    return true;
                }
            }
            return false;
        } else {
            return comparer.compare(methods[0].invoke(o), conditionValue);
        }
    }

    public static ValueMatcher getValueMatcher(Map<String, MethodInfo> getMethodInfos, Where where) throws Exception {
        String operator = where.getO();
        if (!Strings.isNull(operator)) {
            String[] props = where.getK().split("\\s*,\\s*");
            Method[] methods;
            if (props.length > 1) {
                List<Method> methodList = New.list();
                for (int i = 0, L = props.length; i < L; ++i) {
                    String prop = props[i];
                    MethodInfo info = getMethodInfos.get(prop);
                    if (info != null) {
                        methodList.add(info.getMethod());
                    }
                }
                if (methodList.size() == 0) {
                    return null;
                }
                methods = methodList.toArray(new Method[methodList.size()]);
            } else {
                methods = new Method[1];
                MethodInfo firstMethodInfo = getMethodInfos.get(props[0]);
                if (firstMethodInfo == null) {
                    return null;
                }
                methods[0] = firstMethodInfo.getMethod();
            }
            Object value = where.getV();
            Class<?> returnType = methods[0].getReturnType();
            IComparer comparer = null;
            if (operator.trim().toLowerCase().equals("like")) {
                if (value != null && value.toString().trim().length() != 0) {
                    String strValue = value.toString();
                    LikeComparer likeComparer = new LikeComparer(strValue);
                    return new ValueMatcher(methods, likeComparer, strValue);
                } else {
                    return null;
                }
            } else {
                operator = operator.trim();
                comparer = Compares.getComparer(returnType, operator);
                IParser<?, Object> parser = Parsers.getParser(returnType);
                value = parser.parse(value);
                return new ValueMatcher(methods, comparer, value);
            }
        } else {
            return null;
        }
    }

}
