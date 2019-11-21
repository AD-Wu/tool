package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.enums.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

/**
 * @Date 2019-01-13 10:58
 * @Author AD
 */
@Decoder(format = Format.BYTE)
public class ByteDecoder extends BaseDecoder<Byte> {

    @Override
    public Byte decode(final byte[] bs) {
        return new Byte(Converts.toByte(bs));
    }

    @Override
    public Byte decode(final String str) throws Exception {
        return null;
    }

}
