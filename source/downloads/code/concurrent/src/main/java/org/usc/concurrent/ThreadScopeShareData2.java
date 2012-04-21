package org.usc.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 线程范围内的共享数据
 *
 * @author ShunLi
 */
public class ThreadScopeShareData2 {
    private static Map<Thread, Integer> shareData = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    shareData.put(Thread.currentThread(), data);
                    System.out.println(Thread.currentThread().getName() + " put data = " + data);
                    new A().getData();
                    new B().getData();
                }
            }).start();
        }
    }

    static class A {
        public void getData() {
            int data = shareData.get(Thread.currentThread());
            System.out.println("A:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

    static class B {
        public void getData() {
            int data = shareData.get(Thread.currentThread());
            System.out.println("B:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

}
