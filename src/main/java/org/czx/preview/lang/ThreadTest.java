package org.czx.preview.lang;

/**
 * Created by zhixuecai on 2018/11/20.
 */
public class ThreadTest {

    public static void main(String[] args) {
         Thread t1 = new Thread(new Runnable() {
             @Override
             public void run() {
                 System.out.println(Thread.currentThread().getName());
                 System.out.println(Thread.currentThread().getPriority());
//                 try {
//                     Thread.sleep(10000);
//                 } catch (InterruptedException e) {
//                     e.printStackTrace();
//                 }

             }
         });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getPriority());
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getPriority());
            }
        });

        t1.start();
//        try {
//            t1.join(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t2.start();
//        try {
//            t2.join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        t2.start();
        t3.start();
        try {
            t1.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
