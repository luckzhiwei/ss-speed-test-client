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


    public ThreadPool() {
        this(Runtime.getRuntime().availableProcessors()
                , Runtime.getRuntime().availableProcessors(), Constant.TIME_TO_KEPP_ALIVE);
    }


    public ThreadPool(int minSize, int maxSize, int timeToKeepAlive) {
        this.mExecutor = new ThreadPoolExecutor(minSize, maxSize, timeToKeepAlive, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    public void execTask(Runnable task) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(task);
        }
    }

    public void stopNow() {
        this.mExecutor.shutdownNow();
    }

    public void stopAddTask() {
        this.mExecutor.shutdown();
    }

    public void waitAllTaskComplete() throws InterruptedException {
        mExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }


}
