package com.example.demo_activity.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @Author: yongl
 * @DATE: 2020/8/13 15:49
 */

public class TestThreadVariable {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestThreadVariable.class);

    public static void main(String[] args) {
      /*  for (int i = 4; i <10; i++) {
            final int number=i;
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    thread(number);
                }
            },"thread-"+i);
            thread1.start();
        }*/
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <=10 ; i++) {
            list.add(i);
        }
        ListIterator<Integer> integerListIterator = list.listIterator();
        while (integerListIterator.hasNext()){
            integerListIterator.remove();
        }

    }

    private  static void thread(int number){
        LOGGER.info("线程开始的时间为:{},线程名称为:{}",System.currentTimeMillis(),Thread.currentThread().getName());
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <=number ; i++) {
            list.add(i);
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.remove();
        }*/
        for (Integer integer : list) {
            LOGGER.info("当前线程:{},当前值：{}",Thread.currentThread().getName(),integer);
        }
    }
}
