package com.demo01;
// 基本卖票例子

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** 真正的多线程开发，公司中的开发，降低耦合性
 *  线程就是一个单独的资源类，没有任何附属的操作！
 *  1. 属性、方法*/
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发： 多线程操作同一个资源类,把资源类丢入线程
        Ticket2 ticket = new Ticket2();

        // @FunctionalInterface 函数式接口, jdk1.8 lambda 表达式
        new Thread(()->{for (int i = 1; i < 40; i++) ticket.sale();}, "A").start();
        new Thread(()->{for (int i = 1; i < 40; i++) ticket.sale();}, "B").start();
        new Thread(()->{for (int i = 1; i < 40; i++) ticket.sale();}, "C").start();
    }
}

// Lock 三部曲
// 1. new ReentrantLock();
// 2. lock.lock();
// 3. finally => lock.unlock();
class Ticket2{
    // 属性、方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try {
            // 业务代码
            if (number > 0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+number--+"票，剩余： "+number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

