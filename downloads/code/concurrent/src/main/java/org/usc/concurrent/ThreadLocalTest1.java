package org.usc.concurrent;

import java.util.Random;

/**
 * 线程范围内的共享变量
 *
 * @author ShunLi
 */
public class ThreadLocalTest1 {
    private static ThreadLocal<Integer> local = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    local.set(data);

                    System.out.println(Thread.currentThread().getName() + " put data = " + data);
                    new A().getData();
                    new B().getData();
                }
            }).start();
        }
    }

    static class A {
        public void getData() {
            int data = local.get();
            System.out.println("A:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

    static class B {
        public void getData() {
            int data = local.get();
            System.out.println("B:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

}
