package com.x.commons.util.array;

import java.util.Map;

/**
 * @Desc TODO
 * @Date 2019-11-08 14:19
 * @Author AD
 */
public final class Maps {

    private Maps() {}

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

}
