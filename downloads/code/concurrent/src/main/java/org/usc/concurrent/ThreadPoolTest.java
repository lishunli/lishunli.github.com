package org.usc.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的概念与Executors类的应用
 *
 * @author ShunLi
 */
public class ThreadPoolTest {
    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);// 创建固定大小的线程池
        // ExecutorService threadPool = Executors.newCachedThreadPool();// 创建缓存线程池
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();// 创建单一线程池
        // SingleThreadExecutor比普通的单线程好处在于，如果一个线程死了，SingleThreadExecutor 照样会创建另一个单独的线程

        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                public void run() {
                    for (int j = 1; j <= 10; j++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for  task of " + task);
                    }
                }
            });
        }

        System.out.println("all of 10 tasks have committed! ");
        threadPool.shutdown(); // Runnable 全部做完后就结束线程池

        // 用线程池启动定时器
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + " invoked...");

                    }
                },
                6,
                2,
                TimeUnit.SECONDS);
    }
}
