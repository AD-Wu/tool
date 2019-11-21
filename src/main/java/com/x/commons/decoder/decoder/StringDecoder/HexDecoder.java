package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Strings;

import static com.x.commons.decoder.enums.Format.HEX;

/**
 * @Date 2018-12-23 12:29
 * @Author AD
 */

@Decoder(format = HEX)
public class HexDecoder extends BaseDecoder<String> {

    @Override
    public String decode(final byte[] bs) {
        return Strings.toASCII(bs);
    }

}
