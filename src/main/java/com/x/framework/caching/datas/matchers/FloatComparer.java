package com.x.framework.caching.datas.matchers;

import com.x.commons.util.bean.New;

import java.util.Map;

public enum FloatComparer implements IComparer<Float> {
    EQUALS("=") {
        @Override
        public boolean compare(Float first, Float second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS("<>") {
        @Override
        public boolean compare(Float first, Float second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean compare(Float first, Float second) {
            return first > second;
        }
    },

    GREATER_EQUALS(">=") {
        @Override
        public boolean compare(Float first, Float second) {
            return first >= second;
        }
    },
    LESS("<") {
        @Override
        public boolean compare(Float first, Float second) {
            return first < second;
        }
    },
    LESS_EQUALS("<=") {
        @Override
        public boolean compare(Float first, Float second) {
            return first <= second;
        }
    };

    private final String operator;

    private FloatComparer(String operator) {
        this.operator = operator;
    }

    private static final Map<String, IComparer> map = New.concurrentMap();

    public static IComparer getComparer(String operator) {
        return map.get(operator);
    }

    static {
        FloatComparer[] comparers = values();
        for (FloatComparer comparer : comparers) {
            map.put(comparer.operator, comparer);
        }
    }
}
