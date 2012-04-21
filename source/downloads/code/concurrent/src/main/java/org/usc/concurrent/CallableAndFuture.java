package org.usc.concurrent;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // Callable要采用ExecutorSevice的submit方法提交，返回的future对象可以取消任务
        final Future<String> future = threadPool.submit(
                new Callable<String>() {
                    public String call() throws Exception {
                        Thread.sleep(2000);
                        return "hello";
                    };
                }
                );
        // Future取得的结果类型和Callable返回的结果类型必须一致

        // System.out.println("等待结果1");
        // try {
        // System.out.println("拿到结果1：" + future.get());
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        new Thread(new Runnable() {
            public void run() {
                System.out.println("等待结果2");
                try {
                    System.out.println("拿到结果2：" + future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("end..");

        ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
        // CompletionService用于提交一组Callable任务，其take方法返回已完成的一个Callable任务对应的Future对象
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
        for (int i = 1; i <= 10; i++) {
            final int seq = i;
            completionService.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return seq;
                }
            });
        }
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
