package com.x.framework.caching.datas.matchers;

import com.x.commons.util.bean.New;

import java.util.Map;

public enum LongComparer implements IComparer<Long> {
    EQUALS("=") {
        @Override
        public boolean compare(Long first, Long second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS("<>") {
        @Override
        public boolean compare(Long first, Long second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean compare(Long first, Long second) {
            return first > second;
        }
    },

    GREATER_EQUALS(">=") {
        @Override
        public boolean compare(Long first, Long second) {
            return first >= second;
        }
    },
    LESS("<") {
        @Override
        public boolean compare(Long first, Long second) {
            return first < second;
        }
    },
    LESS_EQUALS("<=") {
        @Override
        public boolean compare(Long first, Long second) {
            return first <= second;
        }
    };

    private final String operator;

    private LongComparer(String operator) {
        this.operator = operator;
    }

    private static final Map<String, IComparer> map = New.concurrentMap();

    public static IComparer getComparer(String operator) {
        return map.get(operator);
    }

    static {
        LongComparer[] comparers = values();
        for (LongComparer comparer : comparers) {
            map.put(comparer.operator, comparer);
        }
    }
}
