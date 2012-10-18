package org.usc.concurrent;

/**
 * 传统的创建线程方法
 *
 * @author ShunLi
 */
public class TraditionalThread {

    public static void main(String[] args) {
        // 传统创建线程的第一种方法，创建Thread的子类（也就是继承 Thread），
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1:" + Thread.currentThread().getName());
                    System.out.println("2:" + this.getName());
                }
            }
        };
        thread1.start();

        // 传统创建线程的第二种方法，传递 Runnable作为构造器的参数（也就是实现 Runnable接口），
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("3:" + Thread.currentThread().getName());
                }
            }
        });
        thread2.start();

        // 总结，传统创建Thread有两种方法，一种是继承Thread类，一种是实现Runnable接口
        // 一般使用的话，建议使用第二种方法，也就是实现Runnable接口优于继承 Thread
        // 大致这样认为：
        // 1. Java 是单继承的，如果继承了Thread了，那么就丢失了灵活性
        // 2. 实现Runnable接口更适合于资源的共享，你可以看看上面的构造器，可以传递同一个 Runnable 的实现类。

        // 综合上面两点，你觉得结果如何？
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("runnable :" + Thread.currentThread().getName());

                }
            }
        }) {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread :" + Thread.currentThread().getName());

                }
            }
        }.start();

        // 这就是 override ，所以，执行的就是子类的方法，结果你应该知道了。（无序，小心）
        // output:
        // 1:Thread-0
        // 2:Thread-0
        // thread :Thread-2
        // 3:Thread-1
    }

}
