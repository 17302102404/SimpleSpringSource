package org.czx.preview.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhixuecai on 2018/11/15.
 */
public class MapTest {
    public static void main(String[] args) {
        HashMap map = new HashMap(256);
        Map m = Collections.synchronizedMap(map);
        map.put(null,null);
        System.out.println(map.get(null));
    }
}
