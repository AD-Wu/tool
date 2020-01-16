package com.x.framework.caching.datas.matchers;

import com.x.commons.util.bean.New;

import java.util.Map;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 15:31
 */
public enum ByteComparer implements IComparer<Byte> {
    EQUALS("=") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS("<>") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first > second;
        }
    },

    GREATER_EQUALS(">=") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first >= second;
        }
    },
    LESS("<") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first < second;
        }
    },
    LESS_EQUALS("<=") {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first <= second;
        }
    };


    private final String operator;

    private ByteComparer(String operator) {
        this.operator = operator;
    }

    private static final Map<String, IComparer> map = New.concurrentMap();

    public static IComparer getComparer(String operator) {
        return map.get(operator);
    }

    static {
        ByteComparer[] comparers = values();
        for (ByteComparer comparer : comparers) {
            map.put(comparer.operator, comparer);
        }
    }
}
