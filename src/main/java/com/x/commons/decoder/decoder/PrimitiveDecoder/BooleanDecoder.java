package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.decoder.base.BaseDecoder;

import static com.x.commons.decoder.enums.Format.BOOLEAN;

/**
 * @Date 2019-01-13 11:02
 * @Author AD
 */
@Decoder(format = BOOLEAN)
public class BooleanDecoder extends BaseDecoder<Boolean> {


    @Override
    public Boolean decode(final byte[] bs) {
        return null;
    }

    @Override
    public Boolean decode(final String str) throws Exception {
        return null;
    }

}
