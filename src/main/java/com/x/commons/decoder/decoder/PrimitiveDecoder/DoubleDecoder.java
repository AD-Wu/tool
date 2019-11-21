package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.core.Decoder;
import com.x.commons.decoder.core.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

import java.io.Serializable;

/**
 * @Date 2019-01-13 11:02
 * @Author AD
 */
@Decoder(format = Format.DOUBLE)
public class DoubleDecoder extends BaseDecoder<Double> {

    @Override
    public Double decode(final byte[] bs) {
        return new Double(Converts.toDouble(bs));
    }

    @Override
    public Double decode(final String str) throws Exception {
        return null;
    }

}
