package com.x.commons.decoder.decoder.PrimitiveDecoder;

import com.x.commons.decoder.core.Decoder;
import com.x.commons.decoder.core.Format;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Converts;

/**
 * @Date 2019-01-13 10:59
 * @Author AD
 */
@Decoder(format = Format.SHORT)
public class ShortDecoder extends BaseDecoder<Short> {

    @Override
    public Short decode(final byte[] bs) {
        return new Short(Converts.toShort(bs));
    }

}
