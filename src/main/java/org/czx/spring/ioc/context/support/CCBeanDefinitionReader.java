package org.czx.spring.ioc.context.support;

import org.czx.spring.ioc.beans.CCBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 这个read所在包下为注解的方式
 * Created by zhixuecai on 2018/11/26.
 */
public class CCBeanDefinitionReader {
    private  Properties config = new Properties();

    private List<String> beanNames = new ArrayList<>();

    public  CCBeanDefinitionReader(String confPath){
        reader(confPath);
    }

    public  Properties getConfig() {
        return config;
    }

    //读文件
    public void reader(String... confpath) {
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(confpath[0]);
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //扫描包得到文件名称，基本file的一些操作
    public void doScan(String packagePath) throws NoSuchFileException {
        URL url = this.getClass().getClassLoader().getResource(packagePath.replaceAll("\\.","/"));

        if (url == null) {
            throw new NoSuchFileException(packagePath);
        }
        File classDir = new File(url.getFile());
        for (File fs:classDir.listFiles()) {
            if (fs.isDirectory()) {
                doScan(packagePath+"."+fs.getName());
            } else {
                beanNames.add((packagePath+"."+fs.getName()).replaceAll(".class",""));
            }
        }
    }

    //加载
    public List<String> load(){
        return beanNames;
    }

    //注册
    public CCBeanDefinition register(String className){
        CCBeanDefinition ccBeanDefinition = new CCBeanDefinition();
        ccBeanDefinition.setBeanClassName(className);
        //默认为类首字母小写
        ccBeanDefinition.setBeanFactoryName(className.substring(className.lastIndexOf(".") + 1));
        return ccBeanDefinition;
    }


    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }


}

