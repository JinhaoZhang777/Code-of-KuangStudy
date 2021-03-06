package com.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 * 独占锁（写锁） 一次只能被一个线程占有
 * 共享锁（读锁） 多个线程能同时占有
 * 读-读 可以共存
 * 读-写 不能共存
 * 写-写 不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();

        for (int i=1; i<=5; i++) {
            int temp = i;
            new Thread(()->{
                myCache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        for (int i=1; i<=5; i++) {
            int temp = i;
            new Thread(()->{
                myCache.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}
/**
 * 自定义缓存
 */

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value){
        System.out.println(Thread.currentThread().getName()+"写入"+ key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName()+"写入完毕"+ key);
    }

    public void get(String key){
        System.out.println(Thread.currentThread().getName()+"读取"+ key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName()+"读取完毕"+ key);
    }
}

// 加锁
class MyCacheLock{
    private volatile Map<String, Object> map = new HashMap<>();
    // 读写锁 更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 写入时，只希望一个线程写
    public void put(String key, Object value){
        readWriteLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+"写入"+ key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"写入完毕"+ key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    // 读取时，所有人都可以读！
    public void get(String key){
        readWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName()+"读取"+ key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取完毕"+ key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}