package com.example.demo_activity.thread;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yongl
 * @DATE: 2020/5/9 10:05
 */

public class Mutex {
    private final static Object MUTEX = new Object();

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        for (int i = 0; i < 5; i++) {
            //利用lamba表达式来创建线程
            new Thread(mutex::accessResource).start();
        }
    }
}
