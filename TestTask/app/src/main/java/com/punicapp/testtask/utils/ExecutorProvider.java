package com.punicapp.testtask.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.lang.Thread.MIN_PRIORITY;

public class ExecutorProvider {

    public static final String THREAD_PREFIX = "RestClient-";
    public static final String IDLE_THREAD_NAME = THREAD_PREFIX + "Idle";


    private static ThreadFactory customThreadPoolFactory = new ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setPriority(MIN_PRIORITY);
                    r.run();
                }
            }, IDLE_THREAD_NAME);
        }
    };

    public static Executor defaultHttpExecutor() {
        return Executors.newCachedThreadPool(customThreadPoolFactory);
    }
}