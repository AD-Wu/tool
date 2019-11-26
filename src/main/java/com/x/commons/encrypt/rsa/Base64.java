package com.x.commons.encrypt.rsa;

import com.ax.commons.encrypt.base64.BASE64;
import com.google.common.escape.Escaper;
import com.google.common.io.BaseEncoding;
import com.google.common.xml.XmlEscapers;

/**
 * @Desc TODO
 * @Date 2019-11-25 22:56
 * @Author AD
 */
public class Base64 {
    
    public static void main(String[] args) {
        BaseEncoding base = BaseEncoding.base64();
        BASE64 ax = new BASE64();
        base.encode(new byte[2], 2, 3);
        base.encode(new byte[1]);
        base.encodingStream(null);
        base.encodingSink(null);
        BaseEncoding base32 = BaseEncoding.base32Hex();
    
        Escaper escaper = XmlEscapers.xmlContentEscaper();
        
    
    }
    
}
