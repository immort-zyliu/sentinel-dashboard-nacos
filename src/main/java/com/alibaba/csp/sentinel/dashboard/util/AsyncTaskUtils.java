package com.alibaba.csp.sentinel.dashboard.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  17:50
 */
public class AsyncTaskUtils {

    /**
     * Returns a new CompletableFuture that is asynchronously completed
     * by a task running in the given executor with the value obtained
     * by calling the given Supplier.
     *
     * @param supplier a function returning the value to be used
     * to complete the returned CompletableFuture
     * @param <U> the function's return type
     * @return the new CompletableFuture
     */
    public static <U> CompletableFuture<U> supplyAsyncWithDefaultThreadPool(Supplier<U> supplier) {

        return CompletableFuture.supplyAsync(supplier, IOBoundThreadPool.getIOBoundThreadPool());
    }
}
