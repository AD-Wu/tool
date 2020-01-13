package com.x.framework.caching.datas.matchers;

import java.util.Date;

public enum DateComparer implements IComparer<Date> {
    EQUALS {
        @Override
        public boolean compare(Date first, Date second) {
            if (first == second) {
                return true;
            } else if (first != null && second != null) {
                return first.getTime() == second.getTime();
            }
            return false;
        }
    },
    NO_EQUALS {
        @Override
        public boolean compare(Date first, Date second) {
            return !EQUALS.compare(first, second);
        }
    },
    GREATER {
        @Override
        public boolean compare(Date first, Date second) {
            if (first == second) {
                return false;
            } else if (first != null && second == null) {
                return true;
            } else if (first == null && second != null) {
                return false;
            } else {
                return first.getTime() > second.getTime();
            }
        }
    },

    GREATER_EQUALS {
        @Override
        public boolean compare(Date first, Date second) {
            if (first == second) {
                return true;
            } else if (first != null && second == null) {
                return true;
            } else if (first == null && second != null) {
                return false;
            } else {
                return first.getTime() >= second.getTime();
            }
        }
    },
    LESS {
        @Override
        public boolean compare(Date first, Date second) {
            if (first == second) {
                return false;
            } else if (first != null && second == null) {
                return false;
            } else if (first == null && second != null) {
                return true;
            } else {
                return first.getTime() < second.getTime();
            }
        }
    },
    LESS_EQUALS {
        @Override
        public boolean compare(Date first, Date second) {
            if (first == second) {
                return true;
            } else if (first != null && second == null) {
                return false;
            } else if (first == null && second != null) {
                return true;
            } else {
                return first.getTime() <= second.getTime();
            }
        }
    };
}
