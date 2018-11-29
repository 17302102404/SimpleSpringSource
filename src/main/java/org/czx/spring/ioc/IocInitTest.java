package org.czx.spring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhixuecai on 2018/11/15.
 */
public class IocInitTest {
    public static void main(String[] args) {


        //路径的问题完全参照编译后的结果，有时可能未编译就会存在文件找不到情况
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //这一步就完成了容器的初始化
        System.out.println("success");
        HelloWorld h = context.getBean("helloWorld", HelloWorld.class);
        System.out.println(h.getName());
    }
}
