package com.forsrc.utils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculator<T, R> extends RecursiveTask<R> {

    private static final long serialVersionUID = -7210784648118295671L;

    private SequentialCalculator<T, R> sequentialCalculator;
    private T[] numbers;
    private int start;
    private int end;
    private long threshold = 1000_000;

    private ForkJoinCalculator(T[] numbers, int start, int end, SequentialCalculator<T, R> sequentialCalculator) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.sequentialCalculator = sequentialCalculator;
    }

    public ForkJoinCalculator(T[] numbers, SequentialCalculator<T, R> sequentialCalculator) {
        this(numbers, 0, numbers.length, sequentialCalculator);
    }

    @Override
    protected R compute() {

        int length = end - start;

        if (length <= threshold) {
            return sequentialCalculator.computeSequentially(numbers, start, end);
        }

        ForkJoinCalculator<T, R> leftTask = new ForkJoinCalculator<T, R>(numbers, start, start + length / 2,
                sequentialCalculator);
        leftTask.fork();

        ForkJoinCalculator<T, R> rightTask = new ForkJoinCalculator<T, R>(numbers, start + length / 2, end,
                sequentialCalculator);

        R rightResult = rightTask.compute();

        R leftResult = leftTask.join();

        return sequentialCalculator.compute(rightResult, leftResult);
    }

    public R exec(T[] numbers, SequentialCalculator<T, R> sequentialCalculator) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        return exec(forkJoinPool, numbers, sequentialCalculator);
    }

    public R exec(ForkJoinPool forkJoinPool, T[] numbers, SequentialCalculator<T, R> sequentialCalculator) {
        return forkJoinPool.invoke(new ForkJoinCalculator<T, R>(numbers, sequentialCalculator));
    }

    public static interface SequentialCalculator<T, R> {
        R computeSequentially(T[] numbers, int start, int end);

        R compute(R rightResult, R leftResult);
    }

    public SequentialCalculator<T, R> getSequentialCalculator() {
        return sequentialCalculator;
    }

    public ForkJoinCalculator<T, R> setSequentialCalculator(SequentialCalculator<T, R> sequentialCalculator) {
        this.sequentialCalculator = sequentialCalculator;
        return this;
    }

    public T[] getNumbers() {
        return numbers;
    }

    public ForkJoinCalculator<T, R> setNumbers(T[] numbers) {
        this.numbers = numbers;
        return this;
    }

    public int getStart() {
        return start;
    }

    public ForkJoinCalculator<T, R> setStart(int start) {
        this.start = start;
        return this;
    }

    public int getEnd() {
        return end;
    }

    public ForkJoinCalculator<T, R> setEnd(int end) {
        this.end = end;
        return this;
    }

    public long getThreshold() {
        return threshold;
    }

    public ForkJoinCalculator<T, R> setThreshold(long threshold) {
        this.threshold = threshold;
        return this;
    }

}
