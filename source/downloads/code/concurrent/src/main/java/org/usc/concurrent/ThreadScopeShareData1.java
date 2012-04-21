package org.usc.concurrent;

import java.util.Random;

/**
 * 线程范围内的共享数据
 *
 * @author ShunLi
 */
public class ThreadScopeShareData1 {
    private static int data = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " put data = " + data);
                    new A().getData();
                    new B().getData();
                }
            }).start();
        }
    }

    static class A {
        public void getData() {
            System.out.println("A:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

    static class B {
        public void getData() {
            System.out.println("B:" + Thread.currentThread().getName() + " get data = " + data);
        }
    }

}
