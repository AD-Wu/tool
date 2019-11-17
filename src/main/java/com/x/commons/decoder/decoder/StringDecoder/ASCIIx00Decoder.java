package com.x.commons.decoder.decoder.StringDecoder;

import com.x.commons.decoder.core.Decoder;

import static com.x.commons.decoder.core.Format.ASCII00;

/**
 * @Date 2019-01-03 22:02
 * @Author AD
 */
@Decoder(format = ASCII00)
public class ASCIIx00Decoder extends ASCIIxxBaseDecoder {

    @Override
    protected int getSplit() {return 0;}

}
