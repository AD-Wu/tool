package com.x.protocol.serialize;

import com.x.commons.decoder.Decoders;
import com.x.commons.util.convert.Primitive;
import com.x.protocol.annotations.infoold.DataInfo;
import com.x.protocol.annotations.infoold.FieldInfo;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * @Date 2019-01-02 22:25
 * @Author AD
 */
public class Serializer implements ISerializer {

    @SneakyThrows
    public Serializable serialize(@NonNull DataInfo data, @NonNull ByteBuffer buf) {

        Object req = data.getSelfClass().newInstance();

         FieldInfo[] fis = data.getFieldInfos();

        for (FieldInfo fi : fis) {
             Field f = fi.getField();
            f.setAccessible(true);
            buf.position(buf.position() + fi.getSkipByte());

            switch (fi.getFieldType()) {
                case FORMAT:// String 或 自定义格式化
                    Serializable value = Decoders.getDecoder(fi.getFormat()).decode(fi, buf);
                    if (fi.isPrimitive()) {
                        value = (Serializable) Primitive.of(fi.getFieldClass()).decode(String.valueOf(value));
                    }
                    f.set(req, value);
                    continue;
                case PRIMITIVE:
                    f.set(req, Primitive.of(fi.getFieldClass()).decode(getBytes(buf, fi.getLength())));
                    continue;
                case OBJECT:
                    f.set(req, serialize(fi.getDataInfo(), buf));
                    continue;
                case ARRAY:
                    f.set(req, getArray(req, data, fi, buf));
                    continue;
                case ERROR:
                    throw new RuntimeException("数据定义错误");
                default:
                    break;
            }


        }
        return (Serializable) req;
    }

    // ------------------------------- 私有辅助方法 -------------------------------
    private byte[] getBytes(ByteBuffer buf, int length) {
        length = Math.max(length, 0);
        byte[] bs = new byte[length];
        if (buf.remaining() >= length) {
            buf.get(bs);
        }
        return bs;
    }

    private Object getArray(Object req, DataInfo data, FieldInfo fi, ByteBuffer buf) {
        String lenProp = fi.getLengthProp();
        int arrayLength = (int) data.getFieldValue(lenProp, req); // 数组长度
        final Class<?> component = fi.getComponentClass();
        final Object array = Array.newInstance(component, arrayLength);

        if (fi.isPrimitiveArray()) {
            final int len = fi.getLength();
            for (int i = 0; i < arrayLength; ++i) {
                Array.set(array, i, Primitive.of(component).decode(getBytes(buf, len)));
            }
            return array;
        }

        if (fi.isObjectArray()) {
            for (int i = 0; i < arrayLength; ++i) {
                Array.set(array, i, serialize(fi.getDataInfo(), buf));
            }
        }
        return array;
    }


}
