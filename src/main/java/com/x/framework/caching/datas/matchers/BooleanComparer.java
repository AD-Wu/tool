package com.x.framework.caching.datas.matchers;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 14:53
 */
public enum BooleanComparer implements IComparer<Boolean> {
    EQUALS {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            return first && !second;
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            boolean a = first;
            boolean b = second;
            return a == b || a && !b;
        }
    },
    LESS {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            return !first && second;
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Boolean first, Boolean second) {
            boolean a = first;
            boolean b = second;
            return a == b || !a && b;
        }
    };
}
