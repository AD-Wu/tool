package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.core.Decoder;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

import static com.x.commons.decoder.core.Format.BOOLEAN;

/**
 * @Date 2019-01-13 11:02
 * @Author AD
 */
@Decoder(format = BOOLEAN)
public class BooleanDecoder extends BaseDecoder<Boolean> {

    @Override
    public Boolean decode(final byte[] bs) {
        return new Boolean(Converts.toBoolean(bs));
    }

}
