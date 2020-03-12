package com.x.protocol.test.bean;

import com.x.protocol.anno.coreold.XData;
import com.x.protocol.anno.coreold.XField;
import lombok.Data;

import java.io.Serializable;

import static com.x.commons.decoder.enums.Format.HEX;

/**
 * @Date 2019-01-02 14:51
 * @Author AD
 */

@Data
@XData(doc = "B-Data", cache = false)
public class BData implements Serializable {

    @XField(doc = "Name", length = 3, format = HEX)
    private String name;
    @XField(doc = "Age", length = 1)
    private int age;
    @XField(doc = "Sex", length = 1)
    private boolean sex;
    @XField(doc = "Birthday", length = 3, format = HEX)
    private String birthday;
    @XField(doc = "Length", length = 1)
    private int len;
    @XField(doc = "Array", lengthProp = "len")
    private BBData[] bbDatas;

}
