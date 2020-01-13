package com.x.framework.caching.datas.matchers;

public enum LongComparer implements IComparer<Long> {
    EQUALS {
        @Override
        public boolean compare(Long first, Long second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Long first, Long second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER {
        @Override
        public boolean compare(Long first, Long second) {
            return first > second;
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Long first, Long second) {
            return first >= second;
        }
    },
    LESS {
        @Override
        public boolean compare(Long first, Long second) {
            return first < second;
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Long first, Long second) {
            return first <= second;
        }
    };
}
