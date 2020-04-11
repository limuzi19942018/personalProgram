package com.example.demo_activity;

import com.example.demo_activity.test1.otherclass.Obj;

/**
 * @Author: yongl
 * @DATE: 2020/4/8 18:18
 */

public class TestThreedArraylist {
        public static void main(String[] args) {
            final Obj obj = new Obj();
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String val = null;
                    for (int i = 0; i < 100; i++) {
                        val = "t1: " + i;
                        obj.add(String.valueOf(val));
                        System.out.println("add: " + val);

                        sleep(50);
                    }
                }
            });

            Thread t2 = new Thread(new Runnable() {

                @Override
                public void run() {
                    String val = null;
                    for (int i = 0; i < 100; i++) {
                        val = "t2: " + (100 + i);
                        obj.add(String.valueOf(val));
                        System.out.println("add: " + val);

                        sleep(50);
                    }
                }
            });
            try {
                //Thread.join()方法表示是当该线程执行完操作以后才执行下面的操作
                //同时开启两个线程，两个线程并发执行
                t1.start();
                t1.join(); //等待线程 t1 结束
                t2.start();
                t2.join(); //等待线程 t2 结束
            } catch (Exception e) {
            }
            String name = Thread.currentThread().getName();
            System.out.println("Thread name: " + name + ", Obj size: " + obj.size());
        }

        static void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
}
