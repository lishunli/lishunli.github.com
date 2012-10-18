package org.usc.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Cache
 *
 * @author ShunLi
 */
public class CacheDemo {

    private Map<String, Object> cache = new HashMap<String, Object>();

    public static void main(String[] args) {
        CacheDemo cache = new CacheDemo();
        System.out.println(cache.getData("1"));
        System.out.println(cache.getData("1"));
        System.out.println(cache.getData("2"));
        System.out.println(cache.getData("1"));
    }

    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    public Object getData(String key) {
        rwl.readLock().lock();
        Object value = null;
        try {
            value = cache.get(key);
            if (value == null) {
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    if (value == null) { // 双重检查，防止多线程都进入了写锁等待后，重复写数据
                        System.out.println(key + " not in cache...");
                        value = "aaaa";// 可能查DB 什么的;
                        cache.put(key, value);
                    }
                } finally {
                    rwl.writeLock().unlock();
                }
                rwl.readLock().lock();
            }
        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }
}
