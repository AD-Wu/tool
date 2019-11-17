package com.x.commons.collection;

import com.x.commons.util.array.Arrays;
import com.x.commons.util.array.Maps;
import com.x.commons.util.bean.New;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.convert.Strings;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Desc TODO
 * @Date 2019-11-08 12:06
 * @Author AD
 */
public final class DataSet {

    private Map<String, Object> datas = New.map();

    public DataSet() {
    }

    public DataSet(NameValue... nameValues) {
        if (!Arrays.isEmpty(nameValues)) {
            Stream.of(nameValues).forEach(nv -> datas.put(nv.getName().toUpperCase(), nv.getValue()));
        }

    }

    public void add(Object name, Object value) {
        if (name != null) {
            this.datas.put(Strings.toUppercase(name), value);
        }
    }

    public void addAll(DataSet dataSet) {
        if (!isEmpty(dataSet)) {
            this.datas.putAll(dataSet.datas);
        }
    }

    public void addAll(Map<String, Object> map) {
        if (!Maps.isEmpty(map)) {
            map.entrySet().stream().forEach(next -> datas.put(Strings.toUppercase(next.getKey()), next.getValue()));
        }
    }

    public Map<String, Object> getMap() {
        return this.datas;
    }

    public int size() {
        return this.datas.size();
    }

    public void clear() {
        this.datas.clear();
    }

    public Object remove(Object key) {
        return key == null ? null : datas.remove(Strings.toUppercase(key));
    }

    public boolean containsKey(Object key) {
        return key == null ? false : datas.containsKey(Strings.toUppercase(key));
    }

    public <T> T get(Object key) {
        return key == null ? null : (T) datas.get(Strings.toUppercase(key));
    }

    public String getString(Object key) {
        return getString(key, (String) null);
    }

    public String getString(Object key, String defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        return Optional.ofNullable(value).orElseGet(() -> Strings.isNull(defaultValue) ? "" : defaultValue).toString();
    }

    public short getShort(Object key) {
        return this.getShort(key, (short) 0);
    }

    public short getShort(Object key, short defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Short.TYPE)) {
            try {
                return Short.valueOf(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (short) value;
        }
    }

    public int getInt(Object key) {
        return this.getInt(key, 0);
    }

    public int getInt(Object key, int defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Integer.TYPE)) {
            try {
                return Integer.parseInt(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (int) value;
        }
    }

    public long getLong(Object key) {
        return this.getLong(key, 0L);
    }

    public long getLong(Object key, long defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Long.TYPE)) {
            try {
                return Long.parseLong(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (long) value;
        }
    }

    public double getDouble(Object key) {
        return this.getDouble(key, 0.0D);
    }

    public double getDouble(Object key, double defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Double.TYPE)) {
            try {
                return Double.parseDouble(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (double) value;
        }
    }

    public float getFloat(Object key) {
        return this.getFloat(key, 0.0F);
    }

    public float getFloat(Object key, float defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Float.TYPE)) {
            try {
                return Float.parseFloat(value.toString());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return (float) value;
        }
    }

    public boolean getBoolean(Object key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(Object key, boolean defaultValue) {
        Object value = datas.get(Strings.toUppercase(key));
        value = Optional.ofNullable(value).orElse(defaultValue);
        if (!value.getClass().equals(Boolean.TYPE)) {
            String t = Strings.toUppercase(value);
            return Converts.toBoolean(t);
        } else {
            return (boolean) value;
        }
    }

    @Override
    public String toString() {
        return datas.toString();
    }

    // ---------------------- 辅助方法 ----------------------

    private boolean isEmpty(DataSet dataSet) {
        return dataSet == null || dataSet.datas.size() == 0;
    }

}
