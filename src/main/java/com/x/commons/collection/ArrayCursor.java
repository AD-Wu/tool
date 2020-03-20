package com.x.commons.collection;

import com.x.commons.util.collection.XArrays;

/**
 * @Desc：
 * @Author：AD
 * @Date：2020/3/20 14:29
 */
public class ArrayCursor<T> {
    private final T[] ts;

    private int index;

    private final int count;

    public ArrayCursor(T[] ts, int index) {
        this.ts = ts;
        this.index = index;
        this.count = ts.length;
    }

    public T[] getArray() {
        return this.ts;
    }

    public int getPosition() {
        return this.index - 1;
    }

    public int getCount() {
        return this.count;
    }

    public T current() {
        if (XArrays.isEmpty(ts)) {
            return null;
        } else {
            int pos = index - 1;
            return pos >= 0 && pos < count ? ts[pos] : null;
        }
    }

    public boolean hasNext() {
        return !XArrays.isEmpty(ts) && index < count;
    }

    public synchronized T next() {
        return hasNext() ? ts[index++] : null;
    }
}
