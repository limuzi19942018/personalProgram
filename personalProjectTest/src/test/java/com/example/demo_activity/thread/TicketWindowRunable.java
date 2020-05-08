package com.example.demo_activity.thread;

/**
 * @Author: yongl
 * @DATE: 2020/5/8 17:48
 */

public class TicketWindowRunable implements Runnable {

    private int index=1;

    private final static int MAX=600;

    @Override
    public void run() {
        while (index<=MAX){
            System.out.println(Thread.currentThread()+"的号码是"+(index++));
        }
    }

    public static void main(String[] args) {
       final TicketWindowRunable ticketWindowRunable = new TicketWindowRunable();
       //创建四个线程
        Thread thread1 = new Thread(ticketWindowRunable, "一号窗口");
        Thread thread2 = new Thread(ticketWindowRunable, "二号窗口");
        Thread thread3 = new Thread(ticketWindowRunable, "三号窗口");
        Thread thread4 = new Thread(ticketWindowRunable, "四号窗口");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
