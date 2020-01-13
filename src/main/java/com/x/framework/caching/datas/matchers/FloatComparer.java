package com.x.framework.caching.datas.matchers;

public enum FloatComparer implements IComparer<Float> {
    EQUALS {
        @Override
        public boolean compare(Float first, Float second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Float first, Float second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER {
        @Override
        public boolean compare(Float first, Float second) {
            return first > second;
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Float first, Float second) {
            return first >= second;
        }
    },
    LESS {
        @Override
        public boolean compare(Float first, Float second) {
            return first < second;
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Float first, Float second) {
            return first <= second;
        }
    };
}
