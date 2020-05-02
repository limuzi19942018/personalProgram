package com.example.demo_activity.test1.thread;

import java.util.concurrent.Callable;

/**
 * @Author: yongl
 * @DATE: 2020/4/12 16:52
 */

public class TaskTest implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++){
            sum += i;
        }
        System.out.println("sum的值是："+sum);
        return sum;
    }
}
