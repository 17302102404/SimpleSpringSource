package org.czx.preview.lang;

/**
 * Created by zhixuecai on 2018/11/20.
 */
public class yeildTest {
    public static void main(String[] args) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    Thread.yield();
                    System.out.println(Thread.currentThread().getPriority());
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    Thread.yield();
                    System.out.println(Thread.currentThread().getPriority());
                }
            });

            Thread t3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    Thread.yield();
                    System.out.println(Thread.currentThread().getPriority());
                }
            });

        t1.start();
        t2.start();
        t3.start();
    }
}
