package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.core.Decoder;
import com.x.commons.decoder.core.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

import java.io.Serializable;

/**
 * @Date 2019-01-13 11:01
 * @Author AD
 */
@Decoder(format = Format.LONG)
public class LongDecoder extends BaseDecoder<Long> {

    @Override
    public Long decode(final byte[] bs) {
        return new Long(Converts.toLong(bs));
    }

    @Override
    public Long decode(final String str) throws Exception {
        return null;
    }

}
