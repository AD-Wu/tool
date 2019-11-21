package com.x.commons.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.net.HostAndPort;
import com.google.common.reflect.ClassPath;
import com.google.common.util.concurrent.Atomics;
import com.x.commons.util.bean.New;
import com.x.commons.util.reflact.Loader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Desc TODO
 * @Date 2019-11-20 21:54
 * @Author AD
 */
public final class Threads {

    private Threads() {}

    public static Thread get(String threadName) {
        Map<Thread, StackTraceElement[]> all = Thread.getAllStackTraces();
        AtomicReference<Thread> t = new AtomicReference<>();
        all.forEach((k, v) -> {
            if (k.getName().equals(threadName)) {
                t.set(k);
            }
        });
        return t.get();
    }

    public static void main(String[] args) throws IOException {
    }

}
