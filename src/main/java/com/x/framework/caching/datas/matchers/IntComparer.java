package com.x.framework.caching.datas.matchers;

import com.x.commons.util.bean.New;

import java.util.Map;

public enum IntComparer implements IComparer<Integer> {
    EQUALS("=") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS("<>") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first > second;
        }
    },

    GREATER_EQUALS(">=") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first >= second;
        }
    },
    LESS("<") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first < second;
        }
    },
    LESS_EQUALS(">=") {
        @Override
        public boolean compare(Integer first, Integer second) {
            return first <= second;
        }
    };

    private final String operator;

    private IntComparer(String operator) {
        this.operator = operator;
    }

    private static final Map<String, IComparer> map = New.concurrentMap();

    public static IComparer getComparer(String operator) {
        return map.get(operator);
    }

    static {
        IntComparer[] comparers = values();
        for (IntComparer comparer : comparers) {
            map.put(comparer.operator, comparer);
        }
    }
}
