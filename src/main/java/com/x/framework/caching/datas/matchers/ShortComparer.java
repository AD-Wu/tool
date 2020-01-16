package com.x.framework.caching.datas.matchers;

import com.x.commons.util.bean.New;

import java.util.Map;

public enum ShortComparer implements IComparer<Short> {
    EQUALS("=") {
        @Override
        public boolean compare(Short first, Short second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS("<>") {
        @Override
        public boolean compare(Short first, Short second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean compare(Short first, Short second) {
            return first > second;
        }
    },

    GREATER_EQUALS(">=") {
        @Override
        public boolean compare(Short first, Short second) {
            return first >= second;
        }
    },
    LESS("<") {
        @Override
        public boolean compare(Short first, Short second) {
            return first < second;
        }
    },
    LESS_EQUALS("<=") {
        @Override
        public boolean compare(Short first, Short second) {
            return first <= second;
        }
    };

    private final String operator;

    private ShortComparer(String operator) {
        this.operator = operator;
    }

    private static final Map<String, IComparer> map = New.concurrentMap();

    public static IComparer getComparer(String operator) {
        return map.get(operator);
    }

    static {
        ShortComparer[] comparers = values();
        for (ShortComparer comparer : comparers) {
            map.put(comparer.operator, comparer);
        }
    }
}
