package com.example.demo_activity.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yongl
 * @DATE: 2020/5/7 16:12
 */

public class ThreadDemo1 {
    public static void main(String[] args) {
        try {
            //重写Thread的run方法
            Thread t1 = new Thread() {
                @Override
                public synchronized void run() {
                    System.out.println("我是子线程thread");
                    System.out.println("thread:" + Thread.currentThread().getName());
                }
            };
            t1.start();
            t1.setPriority(4);
            //让t1线程执行完以后再让后面线程执行
            //t1.join();
            //实现Runnable的run方法
            Thread t2 = new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    System.out.println("我是子线程runnable");
                    System.out.println("runnable" + Thread.currentThread().getName());
                }
            });
            t2.start();
            t2.setPriority(8);
            //让t2线程执行完成以后再让下面线程执行
            //t2.join();
            //t1.interrupt();该方法是中断一个线程
            Thread t3 =new Thread(new Runnable() {
                @Override
                public synchronized void run() {
                    System.out.println("我是测试另外一种线程构建方法");
                    System.out.println("runnable+name:" + Thread.currentThread().getName());
                    //给当前线程设置优先级，优先级1-10，不能超过10
                }
            }, "name1");
            t3.start();
            t3.setPriority(6);
            //同上
            //t3.join();
            //主线程休眠20s
            //TimeUnit.SECONDS.sleep(20);
            System.out.println("我是主线程");
            System.out.println("主线程的优先级为"+Thread.currentThread().getPriority());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
