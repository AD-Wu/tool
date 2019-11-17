package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.core.Decoder;
import com.x.commons.decoder.decoder.base.BaseDecoder;
import com.x.commons.util.convert.Strings;

import static com.x.commons.decoder.core.Format.ASCII;

/**
 * @Date 2018-12-23 12:32
 * @Author AD
 */

@Decoder(format = ASCII)
public class ASCIIDecoder extends BaseDecoder<String> {

    @Override
    public String decode(final byte[] bs) {
        return Strings.toASCII(bs);
    }

}
