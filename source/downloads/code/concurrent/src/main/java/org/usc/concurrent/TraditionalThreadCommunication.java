package org.usc.concurrent;

/**
 * 面试题 <br>
 * 子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次， <br>
 * 接着再回到主线程又循环100次，如此循环500次。
 *
 * @author ShunLi
 */
public class TraditionalThreadCommunication {

    public static void main(String[] args) {
        final Business business = new Business();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    business.sub(i);
                }

            }
        }).start();

        for (int i = 1; i <= 50; i++) {
            business.main(i);
        }

    }

}

class Business {
    private boolean bShouldSub = true;

    public synchronized void sub(int i) {
        // while 的使用，请查看 Object 类中关于 wait() 的 JDK 文档。
        // As in the one argument version, interrupts and spurious wakeups are possible, and this method should always be used in a loop
        while (!bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 1; j <= 10; j++) {
            System.out.println("sub thread sequence of " + j + ",loop of " + i);
        }
        bShouldSub = false;
        this.notify();
    }

    public synchronized void main(int i) {
        while (bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 1; j <= 100; j++) {
            System.out.println("main thread sequence of " + j + ",loop of " + i);
        }
        bShouldSub = true;
        this.notify();
    }
}
