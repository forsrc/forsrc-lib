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

    public static void timeout(final Long time, Runnable runnable) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        final Thread targetThread = new Thread(runnable);
        Runnable interrupterRunbable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(time);
                    // targetThread.stop();
                    if (!targetThread.isInterrupted()) {
                        targetThread.interrupt();
                    }
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                    // System.out.println("Not timeout!");
                }
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread interrupterThread = new Thread(interrupterRunbable);
        targetThread.start();
        interrupterThread.start();
        try {
            targetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        interrupterThread.interrupt();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void timeout(final Long time, Handler<Void> handler) {

        timeout(1000L, new Runnable() {
            @Override
            public void run() {
                handler.handle();
            }
        });
    }

    public static abstract class Handler<T> {
        public abstract T handle();

        public boolean isInterrupted() {
            /* try {
                TimeUnit.MICROSECONDS.sleep(1);
                return false;
            } catch (InterruptedException e) {
                return true;
            } */
            return Thread.currentThread().isInterrupted();
        }
    }

    public static void main(String[] args) {
        Long sum = 0L;
        try {
            sum = TimeoutUtils.timeout(1000, TimeUnit.MILLISECONDS, new Callable<Long>() {

                @Override
                public Long call() throws Exception {

                    return System.currentTimeMillis();
                }
            });
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(sum);

        try {
            sum = TimeoutUtils.timeout(1000, TimeUnit.MILLISECONDS, new Callable<Long>() {

                @Override
                public Long call() throws Exception {
                    TimeUnit.MILLISECONDS.sleep(1500);
                    return System.currentTimeMillis();
                }
            });
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(sum);

        timeout(1000L, new Runnable() {
            @Override
            public void run() {
                System.out.println("-->" + System.currentTimeMillis());
                try {
                    for (int i = 0; i < 1000000000; i++) {
                        TimeUnit.MICROSECONDS.sleep(1);
                    }
                    System.out.println("-->" + System.currentTimeMillis());
                    System.out.println("OK");
                } catch (Exception e) {
                    // e.printStackTrace();
                    System.out.println("-->" + System.currentTimeMillis());
                    System.out.println("Timeout!");
                }
            }
        });
    }
}
