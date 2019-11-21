package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Strings;

import static com.x.commons.decoder.enums.Format.UTF8;

/**
 * @Date 2018-12-23 12:34
 * @Author AD
 */

@Decoder(format = UTF8)
public class UTF8Decoder extends BaseDecoder<String> {

    @Override
    public String decode(final byte[] bs) {
        return Strings.toASCII(bs);
    }

}
