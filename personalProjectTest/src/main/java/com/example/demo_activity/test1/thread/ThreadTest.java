package com.example.demo_activity.test1.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: yongl
 * @DATE: 2020/4/12 16:51
 */

public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        TaskTest taskTest = new TaskTest();
        Future<Integer> result = executor.submit(taskTest);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task运行结果"+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}
