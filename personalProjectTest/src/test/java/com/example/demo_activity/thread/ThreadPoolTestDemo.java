package com.example.demo_activity.thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: yongl
 * @DATE: 2020/7/31 9:24
 */

public class ThreadPoolTestDemo {

    @Test
    public void test()throws Exception{
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 8,30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2), Executors.defaultThreadFactory());
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(6);
        long start = System.currentTimeMillis();
        for (int i=0;i<6;i++) {
            final int number=i;
            /*Callable<Integer> task =()-> {
                return threadMethod(number);
            };
            Future<Integer> result = threadPoolExecutor.submit(task);
            try {
                Integer resultNumber = result.get();
                System.out.println("输出结果为："+resultNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
           /* Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    String name = Thread.currentThread().getName();
                    System.out.println("线程开始时间:" + start + "时间精确到s:" + (new Date()).toString() + "当前线程名称:" + name);
                }
            };
            runnable.run();*/
            MyThread myThread = new MyThread();
        }
        //threadPoolExecutor.shutdown();
        /*while (true){
            if(threadPoolExecutor.isTerminated()){
                break;
            }
            //Thread.sleep(50);
        }*/
        long end = System.currentTimeMillis();
        System.out.println("耗时是"+(end-start));
    }

    public static int threadMethod(int number)throws Exception{
        long start = System.currentTimeMillis();
        for (int i=0;i<100000;i++) {
            int a=i*1000;
        }
        String name = Thread.currentThread().getName();
        System.out.println("线程开始时间:"+start+"时间精确到s:"+(new Date()).toString()+"当前线程名称:"+name);
        int a=1/0;
        return number;
    }


    static class  MyThread implements Runnable {
        @Override
        public void run(){
            long start = System.currentTimeMillis();
            String name = Thread.currentThread().getName();
            System.out.println("线程开始时间:" + start + "时间精确到s:" + (new Date()).toString() + "当前线程名称:" + name);
        }
    }

    public static void main(String[] args)throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 8,30, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(2), Executors.defaultThreadFactory());
        //ExecutorService threadPool = new ThreadPoolExecutor(7, 15, 5,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        //ExecutorService threadPool =  Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<>();
        for (int i=0;i<19;i++){
            MyThread myThread = new MyThread();
            //threadPool.submit(myThread);
            //threadPool.execute(myThread);
           /* Callable<Integer> task = new Callable() {
                @Override
                public Object call() throws Exception {
                    //因为父类的call方法抛出异常了，所以子类重写方法也抛出异常
                    threadMethod(1);
                    return 1;
                }
            };*/
            //Future<Integer> submit = threadPool.submit(task);
            //Future<String> submit = threadPool.submit(new TaskWithResult());
            //resultList.add(submit);
            //submit.get();该方法会阻塞
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadMethod(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //即使抛出了运行时异常，这个try catch后面的代码也会执行，原因是run方法不会抛出异常
                        throw  new RuntimeException();
                    }
                }
            });
        }
        threadPool.shutdown();
        while (true){
            if(threadPool.isTerminated()){
                break;
            }
        }
        /*try {
        //捕获异常
            for (Future<String> stringFuture : resultList) {
                stringFuture.get();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }*/
        System.out.println("我是异常以后的代码");
    }

    static class TaskWithResult implements Callable<String> {

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
         *
         * @return
         * @throws Exception
         */
        public String call() throws Exception {
            System.out.println("call()方法被自动调用,干活！！！  " + Thread.currentThread().getName());
            int a=1/0;
            //一个模拟耗时的操作
            for (int i = 999999999; i > 0; i--) ;
            return Thread.currentThread().getName();
        }
    }
}
