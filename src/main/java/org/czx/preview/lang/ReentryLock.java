package org.czx.preview.lang;

/**
 * 可重入锁
 * Created by zhixuecai on 2018/11/20.
 */
public class ReentryLock {

    public static void main(String[] args) {
          Thread t1= new Thread(new Runnable() {
              @Override
              public void run() {
                  test1();
              }
          });

        Thread t2= new Thread(new Runnable() {
            @Override
            public void run() {
                test2();
            }
        });
        t2.start();
        try {
            t2.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.start();
        //t2.start();

    }


    public static synchronized  void  test1(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test2();
        System.out.println("1_over");
    }

    public static synchronized  void  test2(){
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2_over");
    }
}
