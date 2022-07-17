package com.alibaba.csp.sentinel.dashboard.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/2/9  9:18
 * 其实这个应该是可配置的，但是为了减少配置量，所以这里就写死了。
 * <p>
 * 如果有需要的话，可以更改其为可配置的
 * <p>
 * io 密集型线程池
 * <p>
 * I/O密集型（I/O-bound）线程池设计  最佳线程数 = ((线程等待时间+线程cpu时间)/线程cpu时间*cpu数目)
 * 我们假设线程等待时间（查询数据库时间）500ms
 * 线程cpu时间20ms
 * (500ms + 20ms)/20 * processors
 * 520 / 20 * processors = 26 * processors
 * 26有点不好看，抹个0，则直接换成20还行。
 * 平常的话，我们用不到那么多线程，所以说，直接10 * processors ，那么最大就是26 * processor
 * <p>
 * 注意：cpu密集型，请不用使用此线程池，在请求过高的时候，极易导致cpu繁忙。
 * <p>
 * <p>
 * 如果交给java8使用，这里应该换成自定义的。
 *
 *
 * 单例模式
 */
public class IOBoundThreadPool {

    /**
     * 这里为了简便，所以就这样弄了一个简单的，实际中，一定要是可以在配置文件中配置的值
     * 并且是自定义的线程池
     */
    private final ThreadPoolTaskExecutor threadPool;


    /**
     * 获取对象的单例
     */
    public static IOBoundThreadPool getInstance() {
        return IOBoundThreadPoolHolder.getIOBoundThreadPool();
    }

    /**
     * 获取单例对象中的 持有的线程池。
     * @return 线程池
     */
    public static Executor getIOBoundThreadPool() {
        return IOBoundThreadPoolHolder.getIOBoundThreadPool().getThreadPool();
    }

    private IOBoundThreadPool() {
        threadPool = new ThreadPoolTaskExecutor();
        // 初始化线程池
        initThreadPool(threadPool);
    }

    private void initThreadPool(ThreadPoolTaskExecutor taskExecutor) {
        //Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        //定义线程池
        //核心线程数
        taskExecutor.setCorePoolSize(processors * 10);
        //线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(processors * 26);
        //线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(80000);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("invoke-t-pool");
        //线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        //核心线程是否允许超时，默认:false
        taskExecutor.setAllowCoreThreadTimeOut(false);
        //IOC容器关闭时是否阻塞等待剩余的任务执行完成，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        //阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
        taskExecutor.setAwaitTerminationSeconds(10);

        /*
         * 拒绝策略，默认是AbortPolicy
         * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
         * DiscardPolicy：丢弃任务但不抛出异常
         * DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
         * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        taskExecutor.initialize();

    }


    /**
     * 静态内部类的单例模式
     */
    private static class IOBoundThreadPoolHolder {
        private static final IOBoundThreadPool threadPoolInstance = new IOBoundThreadPool();

        private static IOBoundThreadPool getIOBoundThreadPool() {
            return threadPoolInstance;
        }
    }

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

}
