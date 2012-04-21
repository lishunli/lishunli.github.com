package org.usc.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 传统的创建定时器
 *
 * @author ShunLi
 */
public class TraditionalTimer {
    private static int count = 0;

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时器1执行了");
            }
        }, 1000);

        class MyTimerTask extends TimerTask {
            @Override
            public void run() {
                count = (++count) % 2;
                System.out.println("定时器 MyTimerTask 执行了");
                new Timer().schedule(new MyTimerTask(), 2000 + 2000 * count);
            }
        }

        new Timer().schedule(new MyTimerTask(), 2000);

        while (true) {
            System.out.println(new Date());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
