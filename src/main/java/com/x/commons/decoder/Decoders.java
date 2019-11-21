package com.x.commons.decoder;

import com.x.commons.decoder.annotation.Decoder;
import com.x.commons.decoder.core.IDecoder;
import com.x.commons.decoder.enums.Format;
import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Clazzs;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @Date 2018-12-22 20:34
 * @Author AD
 */
public final class Decoders {

    private static final Map<Format, IDecoder> DECODERS = New.map();

    public static IDecoder getDecoder(Format format) {
        return DECODERS.get(format);
    }

    static {
        init();
    }

    @SneakyThrows
    private static void init() {
        String name = Decoders.class.getPackage().getName();
        for (Class<?> c : Clazzs.getClass(name, Decoder.class)) {

            final Decoder info = (Decoder) c.getAnnotation(Decoder.class);

            if (info.format() != Format.NULL) {
                DECODERS.put(info.format(), (IDecoder) c.newInstance());
            }
        }
    }

}
