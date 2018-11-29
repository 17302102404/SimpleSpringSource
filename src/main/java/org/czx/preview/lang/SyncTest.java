package org.czx.preview.lang;

/**
 * Created by zhixuecai on 2018/11/20.
 */
public class SyncTest {

    private static int count=1;

    public static void main(String[] args) {
        synchronized (SyncTest.class){
            count++;
        }
    }

}
