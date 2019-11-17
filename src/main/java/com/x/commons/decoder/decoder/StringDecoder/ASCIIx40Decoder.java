package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.core.Decoder;

import static com.x.commons.decoder.core.Format.ASCII40;

/**
 * @Date 2019-01-26 14:28
 * @Author AD
 */
@Decoder(format = ASCII40)
public class ASCIIx40Decoder extends ASCIIxxBaseDecoder {

    @Override
    protected int getSplit() {
        return ',';
    }

}
