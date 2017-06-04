package com.forsrc.utils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculator2<R> extends RecursiveTask<R> {

    private static final long serialVersionUID = 2923937497391940614L;

    private SequentialCalculator<R> sequentialCalculator;
    private Object arrays;
    private int start;
    private int end;
    private long threshold = 1000 * 1000;

    private ForkJoinCalculator2(Object arrays, int start, int end, SequentialCalculator<R> sequentialCalculator) {
        this.arrays = arrays;
        this.start = start;
        this.end = end;
        this.sequentialCalculator = sequentialCalculator;
    }

    public ForkJoinCalculator2(Object arrays, int length, SequentialCalculator<R> sequentialCalculator) {
        this(arrays, 0, length, sequentialCalculator);
    }

    @Override
    protected R compute() {

        int length = end - start;

        if (length <= threshold) {
            return sequentialCalculator.computeSequentially(arrays, start, end);
        }

        ForkJoinCalculator2<R> leftTask = new ForkJoinCalculator2<R>(arrays, start, start + length / 2,
                sequentialCalculator);
        leftTask.fork();

        ForkJoinCalculator2<R> rightTask = new ForkJoinCalculator2<R>(arrays, start + length / 2, end,
                sequentialCalculator);

        R rightResult = rightTask.compute();

        R leftResult = leftTask.join();

        return sequentialCalculator.compute(rightResult, leftResult);
    }

    public R exec(Object arrays, int length, SequentialCalculator<R> sequentialCalculator) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        return exec(forkJoinPool, arrays, length, sequentialCalculator);
    }

    public R exec(ForkJoinPool forkJoinPool, Object arrays, int length, SequentialCalculator<R> sequentialCalculator) {
        return forkJoinPool.invoke(new ForkJoinCalculator2<R>(arrays, length, sequentialCalculator));
    }

    public static interface SequentialCalculator<R> {
        R computeSequentially(Object arrays, int start, int end);

        R compute(R rightResult, R leftResult);
    }

    public SequentialCalculator<R> getSequentialCalculator() {
        return sequentialCalculator;
    }

    public ForkJoinCalculator2<R> setSequentialCalculator(SequentialCalculator<R> sequentialCalculator) {
        this.sequentialCalculator = sequentialCalculator;
        return this;
    }

    public Object getArrays() {
        return arrays;
    }

    public ForkJoinCalculator2<R> setArrays(Object arrays) {
        this.arrays = arrays;
        return this;
    }

    public int getStart() {
        return start;
    }

    public ForkJoinCalculator2<R> setStart(int start) {
        this.start = start;
        return this;
    }

    public int getEnd() {
        return end;
    }

    public ForkJoinCalculator2<R> setEnd(int end) {
        this.end = end;
        return this;
    }

    public long getThreshold() {
        return threshold;
    }

    public ForkJoinCalculator2<R> setThreshold(long threshold) {
        this.threshold = threshold;
        return this;
    }

}
