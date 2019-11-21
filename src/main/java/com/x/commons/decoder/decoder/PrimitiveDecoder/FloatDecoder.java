package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.enums.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

/**
 * @Date 2019-01-13 11:01
 * @Author AD
 */
@Decoder(format = Format.FLOAT)
public class FloatDecoder extends BaseDecoder<Float> {

    @Override
    public Float decode(final byte[] bs) {
        return new Float(Converts.toFloat(bs));
    }


}
