package com.mz.jarboot.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂，生产特定前缀名称的线程
 * @author majianzheng
 */
public class JarbootThreadFactory implements ThreadFactory {
    public static final int MAX_THREAD_SIGN = 100000;
    private AtomicInteger signGenerator = new AtomicInteger(0);
    private String prefix;
    private boolean daemon;

    public static ThreadFactory createThreadFactory(String prefix) {
        return createThreadFactory(prefix, false);
    }

    public static ThreadFactory createThreadFactory(String prefix, boolean daemon) {
        return new JarbootThreadFactory(prefix, daemon);
    }

    private JarbootThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        int i = signGenerator.getAndIncrement();
        if (i > MAX_THREAD_SIGN) {
            signGenerator.set(0);
        }
        Thread thread = new Thread(r);
        thread.setName(String.format("%s-%d", prefix, i));
        thread.setDaemon(this.daemon);
        return thread;
    }
}
