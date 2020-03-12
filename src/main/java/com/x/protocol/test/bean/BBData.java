package com.x.protocol.test.bean;

import com.x.protocol.anno.coreold.XData;
import com.x.protocol.anno.coreold.XField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Date 2019-01-05 20:54
 * @Author AD
 */
@Data
@XData(doc = "BBData", cache = false)
public class BBData implements Serializable {

    @XField(doc = "b_name", length = 3, skipByte = 1)
    private String b_name;

    @XField(doc = "b_age", length = 1)
    private int b_age;


}
