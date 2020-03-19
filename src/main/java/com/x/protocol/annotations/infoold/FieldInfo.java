package com.x.protocol.annotations.infoold;

import com.x.commons.decoder.enums.Format;
import com.x.commons.util.convert.Primitive;
import com.x.protocol.annotations.coreold.XField;
import lombok.Data;
import lombok.NonNull;

import java.lang.reflect.Field;

import static com.x.commons.decoder.enums.Format.HEX;
import static com.x.commons.decoder.enums.Format.NULL;
import static com.x.protocol.annotations.infoold.FieldInfo.FieldType.*;

/**
 * @Date 2018-12-30 23:06
 * @Author AD
 */
@Data
public final class FieldInfo {
    
    private String sid;
    
    private String doc;
    
    private int skipByte;
    
    private int length;
    
    private int min;
    
    private int max;
    
    private String lengthProp;
    
    private Format format;
    
    private boolean pk;
    
    private Field field;
    
    private Class<?> fieldClass;
    
    private Class<?> componentClass;
    
    private DataInfo dataInfo; // 当属性是对象时才不为null
    
    private FieldType fieldType;
    
    public FieldInfo(@NonNull Field field) {
        if (field.isAnnotationPresent(XField.class)) {
            final XField x = field.getAnnotation(XField.class);
            this.pk = x.pk();
            this.sid = x.sid();
            this.doc = x.doc();
            this.min = x.min();
            this.max = x.max();
            this.skipByte = x.skipByte();
            this.lengthProp = x.lengthProp();
            this.field = field;
            this.fieldClass = field.getType();
            this.length = initLength(x);
            this.format = initFormat(x);
            this.componentClass = initComponentClass();
            this.dataInfo = initDataInfo();
            this.fieldType = initFieldType();
        } else {
            this.pk = false;
            this.sid="";
            this.doc="";
            this.min=0;
            this.max=0;
            this.skipByte=0;
            this.lengthProp="";
            this.field=field;
            this.fieldClass=field.getType();
            this.length=Primitive.getLength(fieldClass);
            this.format= NULL;
            this.componentClass=initComponentClass();
            this.dataInfo=initDataInfo();
            this.fieldType=initFieldType();
            
            
        }
    }
    
    public boolean isPrimitive()      { return fieldClass.isPrimitive(); }
    
    public boolean isString()         { return String.class == fieldClass; }
    
    public boolean isObject()         { return !isPrimitive() && !isString() && !isArray(); }
    
    public boolean isPrimitiveArray() { return isArray() && componentClass.isPrimitive();}
    
    public boolean isObjectArray()    { return isArray() && !componentClass.isPrimitive();}
    
    public boolean needFormat()       { return format != NULL; }
    
    public boolean isArray()          { return fieldClass.isArray(); }
    
    public enum FieldType {
        FORMAT,
        PRIMITIVE,
        STRING,
        OBJECT,
        ARRAY,
        ERROR;
    }
    
    // ---------------------------- 私有辅助方法 ----------------------------
    
    private int initLength(XField x) {
        return x.length() == 0 ? Primitive.getLength(fieldClass) : x.length();
    }
    
    private Format initFormat(XField x) {
        return x.format() == NULL ? (String.class.equals(fieldClass) ? HEX : NULL) : x.format();
    }
    
    private Class<?> initComponentClass() {
        return isArray() ? fieldClass.getComponentType() : null;
    }
    
    private DataInfo initDataInfo() {
        return isObject() ? new DataInfo(fieldClass) : isObjectArray() ? new DataInfo(componentClass) : null;
    }
    
    private FieldType initFieldType() {
        return needFormat() ? FORMAT :
                isPrimitive() ? PRIMITIVE :
                        isString() ? STRING :
                                isObject() ? OBJECT :
                                        isArray() ? ARRAY : ERROR;
        
    }
    
}
