package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.util.Constant;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lzw on 17-9-10.
 */

public class ThreadPool {

    private ThreadPoolExecutor mExecutor;

    private ThreadPool(int minSize, int maxSize, int timeToKeepAlive) {
        this.mExecutor = new ThreadPoolExecutor(minSize, maxSize, timeToKeepAlive, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    private static ThreadPool instance;

    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                int num = Runtime.getRuntime().availableProcessors();
                instance = new ThreadPool(num, num, Constant.TIME_TO_KEPP_ALIVE);
            }
        }
        return instance;
    }

    public void execTask(Runnable task) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(task);
        }
    }

    public void stopAddTask(){
        this.mExecutor.shutdown();
    }

    public void waitAllTaskComplete() throws InterruptedException {
        mExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }


}
