package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.enums.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

/**
 * @Date 2019-01-13 11:00
 * @Author AD
 */
@Decoder(format = Format.INTEGER)
public class IntegerDecoder extends BaseDecoder<Integer> {

    @Override
    public Integer decode(final byte[] bs) {
        return new Integer(Converts.toInt(bs));
    }

    @Override
    public Integer decode(final String str) throws Exception {
        return null;
    }

}
