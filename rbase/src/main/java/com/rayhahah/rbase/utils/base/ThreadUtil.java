package com.rayhahah.rbase.utils.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 1. newCachedThreadPool
 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * <p>
 * 2. newFixedThreadPool
 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * <p>
 * 3. newScheduledThreadPool
 * 创建一个定长线程池，支持定时及周期性任务执行。
 * <p>
 * 4. newSingleThreadExecutor
 * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * <p>
 * <p>
 * excute(Runable runable) : 执行Runable,没有返回值
 * submit(Runable runable) : 有Future作为返回值 ， 任务处理完成的时候，future.get()返回Null(阻塞的)
 * submit(Callable call)   : Callable 等于 有返回值的Runable
 */
public class ThreadUtil {

    private static int FIX_POOL = 10;
    private static volatile ExecutorService mExecutorService;
    /**
     * The default rejected execution handler.
     */
    private static final RejectedExecutionHandler defaultHandler = new ThreadPoolExecutor.AbortPolicy();

    private ThreadUtil() {
    }

    private static void initThreadPool() {
        if (mExecutorService == null) {
            synchronized (ThreadUtil.class) {
                if (mExecutorService == null) {
                    //效果是一样的
//                    mExecutorService = Executors.newFixedThreadPool(FIX_POOL);
                    mExecutorService = new ThreadPoolExecutor(FIX_POOL, FIX_POOL,
                            0L, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),defaultHandler);
                }
            }
        }
    }


    public static void excute(Runnable r) {
        if (mExecutorService == null) {
            initThreadPool();
        }
        mExecutorService.execute(r);
    }

    public static Future submit(Runnable runnable) {
        if (mExecutorService == null) {
            initThreadPool();
        }
        return mExecutorService.submit(runnable);
    }

    public static Future submit(Callable callable) {
        if (mExecutorService == null) {
            initThreadPool();
        }
        return mExecutorService.submit(callable);
    }

    public static void shutDown() {
        if (mExecutorService != null
                && !mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
        }
    }
}