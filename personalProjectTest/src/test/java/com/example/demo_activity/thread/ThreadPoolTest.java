package com.example.demo_activity.thread;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池ThreadPoolExecutor的使用方法
 * @Author: yongl
 * @DATE: 2020/7/25 22:26
 */

public class ThreadPoolTest {

    @Test
    public void testOne()throws Exception{
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("--helloWorld_001--" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //任务2
        pool.execute(() -> System.out.println("--helloWorld_002--" + Thread.currentThread().getName()));
    }

    @Test
    public void testTwo()throws Exception{
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("--helloWorld_001--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println("--helloWorld_002--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务3
        pool.execute(() -> System.out.println("--helloWorld_003--" + Thread.currentThread().getName()));
    }

    /**
     * 理论：当队列里的任务达到上限，并且池中正在进行的线程小于maxinumPoolSize,对于新加入的任务，新建线程。
     * 结果：结果：任务1，2启动后 任务3在队列 ，队列就满了，由于正在进行的线程数是2<maximumPoolSize，只能新建一个线程了
     * 然后任务4就进了新线程-3，任务4结束，队列里的任务3在线程3 进行。
     * @throws Exception
     */
    @Test
    public void testThree()throws Exception{
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("--helloWorld_001--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println("--helloWorld_002--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务3
        pool.execute(() -> System.out.println("--helloWorld_003--" + Thread.currentThread().getName()));
        // 任务4
        pool.execute(() -> System.out.println("--helloWorld_004--" + Thread.currentThread().getName()));
    }


    /**
     * 理论：队列里的任务达到上限，并且池中正在运行的线程等于maximumPoolSize，对于新加入的任务，执行拒绝策略（线程池默认的策略是抛异常）。
     *
     * 结果：抛出异常：任务一和任务二阻塞占了两个线程，当执行任务三时，核心线程已经用完，任务三会加入到队列中，
     *      当任务四开始时，会开启另外一个线程（maxPoolSize-corePoolSize=1）,但是任务三也被阻塞，所以当任务五开始时，
     *      已经没有线程可用，就会报异常。
     *
     */
    @Test
    public void testFour(){
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 3, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
        // 任务1
        pool.execute(() -> {
            try {
                Thread.sleep(3 * 1000);
                System.out.println("--helloWorld_001--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务2
        pool.execute(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println("--helloWorld_002--" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 任务3
        pool.execute(() -> System.out.println("--helloWorld_003--" + Thread.currentThread().getName()));
        // 任务4
        pool.execute(() -> {
            try {
                Thread.sleep(2 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--helloWorld_004--" + Thread.currentThread().getName());
        });
        // 任务5
        pool.execute(() -> System.out.println("--helloWorld_005--" + Thread.currentThread().getName()));
    }
}
