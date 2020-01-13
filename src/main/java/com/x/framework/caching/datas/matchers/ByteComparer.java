package com.x.framework.caching.datas.matchers;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/1/13 15:31
 */
public enum ByteComparer implements IComparer<Byte> {
    EQUALS {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first.compareTo(second) == 0;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first.compareTo(second) != 0;
        }
    },
    GREATER {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first > second;
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first >= second;
        }
    },
    LESS {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first < second;
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Byte first, Byte second) {
            return first <= second;
        }
    };
}
