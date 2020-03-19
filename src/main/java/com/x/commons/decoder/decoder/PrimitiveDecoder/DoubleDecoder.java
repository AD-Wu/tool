package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.enums.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

/**
 * @Date 2019-01-13 11:02
 * @Author AD
 */
@Decoder(format = Format.DOUBLE)
public class DoubleDecoder extends BaseDecoder<Double> {

    @Override
    public Double decode(final byte[] bs) {
        return Converts.toDouble(bs);
    }



}
