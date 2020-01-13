package com.x.framework.caching.datas.matchers;

public enum ShortComparer implements IComparer<Short> {
    EQUALS {
        @Override
        public boolean compare(Short first, Short second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Short first, Short second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER {
        @Override
        public boolean compare(Short first, Short second) {
            return first > second;
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Short first, Short second) {
            return first >= second;
        }
    },
    LESS {
        @Override
        public boolean compare(Short first, Short second) {
            return first < second;
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Short first, Short second) {
            return first <= second;
        }
    };
}
