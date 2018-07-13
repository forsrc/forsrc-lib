package com.forsrc.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The type My lock.
 */
public class MyLock {
    private ReentrantReadWriteLock lock;
    private Lock readLock;
    private Lock writeLock;

    /**
     * Instantiates a new My lock.
     */
    public MyLock() {
        this.lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    /**
     * Todo read lock.
     *
     * @param todoReadLock
     *            the todo read lock
     * @throws Exception
     *             the exception
     */
    public void todoReadLock(TodoReadLock todoReadLock) throws Exception {
        this.readLock.lock();
        try {
            todoReadLock.todo();
        } catch (Exception e) {
            throw e;
        } finally {
            this.readLock.unlock();
        }
    }

    /**
     * Todo write lock.
     *
     * @param todoWriteLock
     *            the todo write lock
     * @throws Exception
     *             the exception
     */
    public void todoWriteLock(TodoWriteLock todoWriteLock) throws Exception {
        this.writeLock.lock();
        try {
            todoWriteLock.todo();
        } catch (Exception e) {
            throw e;
        } finally {
            this.writeLock.unlock();
        }
    }

    /**
     * Gets read lock.
     *
     * @return the read lock
     */
    public Lock getReadLock() {
        return readLock;
    }

    /**
     * Gets write lock.
     *
     * @return the write lock
     */
    public Lock getWriteLock() {
        return writeLock;
    }

    /**
     * The interface Todo read lock.
     */
    public interface TodoReadLock {
        /**
         * Todo.
         *
         * @throws Exception
         *             the exception
         */
        void todo() throws Exception;
    }

    /**
     * The interface Todo write lock.
     */
    public interface TodoWriteLock {
        /**
         * Todo.
         *
         * @throws Exception
         *             the exception
         */
        void todo() throws Exception;
    }

    private static final ConcurrentHashMap<Integer, Integer> LOCK = new ConcurrentHashMap<>();

    public static Integer getLock(int number) {
        Integer lock = null;
        synchronized (LOCK) {
            lock = LOCK //
                    .entrySet() //
                    .stream() //
                    .filter(e -> e.getValue().equals(number)) //
                    .map(Map.Entry::getKey) //
                    .findFirst() //
                    .orElse(null); //
            if (lock == null) {
                lock = new Integer(number);
                LOCK.putIfAbsent(lock, number);
            }
        }
        return lock;
    }
}
