package com.forsrc.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TimeoutUtils {

    public static <T> T timeout(long time, TimeUnit timeUnit, Callable<T> callable)
            throws TimeoutException, InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try {
            Future<T> future = executorService.submit(callable);
            T rt = future.get(time, timeUnit);
            return rt;
        } catch (TimeoutException te) {
            throw te;
        } catch (InterruptedException ie) {
            throw ie;
        } catch (ExecutionException ee) {
            throw ee;
        } finally {
            executorService.shutdown();
        }
    }

    public static void main(String[] args) throws TimeoutException, InterruptedException, ExecutionException {
        Long sum = TimeoutUtils.timeout(1000, TimeUnit.MILLISECONDS, new Callable<Long>() {

            @Override
            public Long call() throws Exception {

                return System.currentTimeMillis();
            }
        });
        System.out.println(sum);

        sum = TimeoutUtils.timeout(1000, TimeUnit.MILLISECONDS, new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(1500);
                return System.currentTimeMillis();
            }
        });
        System.out.println(sum);

    }
}
