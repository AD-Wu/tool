package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.core.IDecoder;
import com.x.commons.util.convert.Strings;
import com.x.protocol.anno.info.FieldInfo;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @Date 2019-01-26 14:24
 * @Author AD
 */
public abstract class ASCIIxxBaseDecoder implements IDecoder<String> {

    @Override
    public final String decode(final FieldInfo fieldInfo, final ByteBuffer buf) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte b = buf.get();
        while (b != getSplit()) {
            out.write(b);
            b = buf.get();
        }
        return Strings.toASCII(out.toByteArray());
    }

    protected abstract int getSplit();


}
