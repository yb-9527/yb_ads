package com.by.sdk.byad.utils;

import android.os.Build;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MyThreadPools {
    private static ExecutorService THREAD_POOL_EXECUTOR;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT-1,4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 3+1;
    private static final int KEEP_ALIVE_SECONDS = 10;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(8);

    private void initThreadPool(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,sPoolWorkQueue,new RejectedHandler());
        //允许核心线程空闲超时时被回收
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        THREAD_POOL_EXECUTOR = threadPoolExecutor;
    }

    private class RejectedHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        }
    }


    private MyThreadPools(){
        initThreadPool();
    }

    public static MyThreadPools getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static MyThreadPools instance = new MyThreadPools();
    }

    public void execute(Runnable command){
        THREAD_POOL_EXECUTOR.execute(command);
    }
}
