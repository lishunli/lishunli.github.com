package org.usc.concurrent;

import java.util.Random;

/**
 * 线程范围内的共享变量
 *
 * @author ShunLi
 */
public class ThreadLocalTest2 {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName() + " has put data :" + data);

                    MyThreadScopeData.getThreadInstance().setName("name" + data);
                    MyThreadScopeData.getThreadInstance().setAge(data);

                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A {
        public void get() {
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println("A from " + Thread.currentThread().getName() + " getMyData: " + myData.getName() + "," + myData.getAge());
        }
    }

    static class B {
        public void get() {
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println("B from " + Thread.currentThread().getName() + " getMyData: " + myData.getName() + "," + myData.getAge());
        }
    }
}

class MyThreadScopeData {
    private MyThreadScopeData() {
    }

    // Bob Lee 解决双重加锁问题
    static class Instance {
        static MyThreadScopeData getInstance() {
            return new MyThreadScopeData();
        }
    }

    public static MyThreadScopeData getThreadInstance() {
        MyThreadScopeData instance = map.get();
        if (instance == null) {
            instance = Instance.getInstance();
            map.set(instance);
        }
        return instance;
    }

    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
