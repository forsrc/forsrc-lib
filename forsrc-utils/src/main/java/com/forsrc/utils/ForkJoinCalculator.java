package com.forsrc.utils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculator<T, R> extends RecursiveTask<R> {

    private static final long serialVersionUID = -7210784648118295671L;

    private SequentialCalculator<T, R> sequentialCalculator;
    private ComputeCalculator<T, R> computeCalculator;
    private T[] numbers;
    private int start;
    private int end;
    private long threshold = 1000_000;

    private ForkJoinCalculator(T[] numbers, int start, int end, SequentialCalculator<T, R> sequentialCalculator,
            ComputeCalculator<T, R> computeCalculator) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.sequentialCalculator = sequentialCalculator;
        this.computeCalculator = computeCalculator;
    }

    public ForkJoinCalculator(T[] numbers, SequentialCalculator<T, R> sequentialCalculator,
            ComputeCalculator<T, R> computeCalculator) {
        this(numbers, 0, numbers.length, sequentialCalculator, computeCalculator);
    }

    @Override
    protected R compute() {

        int length = end - start;

        if (length <= threshold) {
            return sequentialCalculator.computeSequentially(numbers, start, end);
        }

        ForkJoinCalculator<T, R> leftTask = new ForkJoinCalculator<T, R>(numbers, start, start + length / 2,
                sequentialCalculator, computeCalculator);
        leftTask.fork();

        ForkJoinCalculator<T, R> rightTask = new ForkJoinCalculator<T, R>(numbers, start + length / 2, end,
                sequentialCalculator, computeCalculator);

        R rightResult = rightTask.compute();

        R leftResult = leftTask.join();

        return computeCalculator.compute(rightResult, leftResult);
    }

    public R exec(T[] numbers, SequentialCalculator<T, R> sequentialCalculator,
            ComputeCalculator<T, R> computeCalculator) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        return exec(forkJoinPool, numbers, sequentialCalculator, computeCalculator);
    }

    public R exec(ForkJoinPool forkJoinPool, T[] numbers, SequentialCalculator<T, R> sequentialCalculator,
            ComputeCalculator<T, R> computeCalculator) {
        return forkJoinPool.invoke(new ForkJoinCalculator<T, R>(numbers, sequentialCalculator, computeCalculator));
    }

    public static interface ComputeCalculator<T, R> {
        public R compute(R rightResult, R leftResult);
    }

    public static interface SequentialCalculator<T, R> {
        R computeSequentially(T[] numbers, int start, int end);
    }

    public SequentialCalculator<T, R> getSequentialCalculator() {
        return sequentialCalculator;
    }

    public ForkJoinCalculator<T, R> setSequentialCalculator(SequentialCalculator<T, R> sequentialCalculator) {
        this.sequentialCalculator = sequentialCalculator;
        return this;
    }

    public ComputeCalculator<T, R> getComputeCalculator() {
        return computeCalculator;
    }

    public ForkJoinCalculator<T, R> setComputeCalculator(ComputeCalculator<T, R> computeCalculator) {
        this.computeCalculator = computeCalculator;
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
