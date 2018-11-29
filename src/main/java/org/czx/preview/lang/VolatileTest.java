package org.czx.preview.lang;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhixuecai on 2018/11/20.
 */
public class VolatileTest {
    private volatile  static  int count=2;

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"抢到了");
                    count--;
                }else{
                    System.out.println(Thread.currentThread().getName()+"没了");
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"抢到了");
                    count--;
                }else{
                    System.out.println(Thread.currentThread().getName()+"没了");
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"抢到了");
                    count--;
                }else{
                    System.out.println(Thread.currentThread().getName()+"没了");
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"抢到了");
                    count--;
                }else{
                    System.out.println(Thread.currentThread().getName()+"没了");
                }
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"抢到了");
                    count--;
                }else{
                    System.out.println(Thread.currentThread().getName()+"没了");
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
