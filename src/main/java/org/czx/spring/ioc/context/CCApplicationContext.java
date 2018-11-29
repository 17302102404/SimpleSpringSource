package org.czx.spring.ioc.context;

import org.czx.spring.ioc.annotation.CCAutowired;
import org.czx.spring.ioc.annotation.CCController;
import org.czx.spring.ioc.annotation.CCService;
import org.czx.spring.ioc.aop.CCAopConfig;
import org.czx.spring.ioc.beans.CCBeanDefinition;
import org.czx.spring.ioc.beans.CCBeanWrapper;
import org.czx.spring.ioc.context.support.CCBeanDefinitionReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhixuecai on 2018/11/26.
 */
public class CCApplicationContext {
    private String confpath;

    private ConcurrentHashMap<String,CCBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private CCBeanDefinitionReader reader;

    //用来保证注册式单例的容器
    private Map<String,Object> beanCacheMap = new HashMap<String, Object>();

    //用来存储所有的被代理过的对象
    private Map<String,CCBeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, CCBeanWrapper>();


    public CCApplicationContext(String confPath){
        this.confpath=confPath;
        refresh();
    }

    private void refresh() {
        //定位
        this.reader = new CCBeanDefinitionReader(confpath);

        //加载
        List<String> beanName = reader.load();

        //注册
        doRegisty(beanName);

        //自动注入
        doAutowrited();

    }


    private void doRegisty(List<String> beanDefinitions) {
        //beanName有三种情况:
        //1、默认是类名首字母小写
        //2、自定义名字
        //3、接口注入
        try {
            for (String className : beanDefinitions) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if(beanClass.isInterface()){ continue; }

                CCBeanDefinition beanDefinition = reader.register(className);
                if(beanDefinition != null){
                    this.beanDefinitionMap.put(beanDefinition.getBeanFactoryName(),beanDefinition);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //开始执行自动化的依赖注入
    private void doAutowrited() {
        for(Map.Entry<String,CCBeanDefinition> beanDefinitionEntry : this.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                Object obj = getBean(beanName);
            }
        }
        for(Map.Entry<String,CCBeanWrapper> beanWrapperEntry : this.beanWrapperMap.entrySet()){

            populateBean(beanWrapperEntry.getKey(),beanWrapperEntry.getValue().getOriginalInstance());

        }
    }


    private CCAopConfig instantionAopConfig(CCBeanDefinition beanDefinition) throws  Exception{
        CCAopConfig config = new CCAopConfig();
        String expression = reader.getConfig().getProperty("pointCut");
        String[] before = reader.getConfig().getProperty("aspectBefore").split("\\s");
        String[] after = reader.getConfig().getProperty("aspectAfter").split("\\s");

        String className = beanDefinition.getBeanClassName();
        Class<?> clazz = Class.forName(className);

        Pattern pattern = Pattern.compile(expression);

        Class aspectClass = Class.forName(before[0]);
        //在这里得到的方法都是原生的方法
        for (Method m : clazz.getMethods()){
            //public .* com\.gupaoedu\.vip\.spring\.demo\.service\..*Service\..*\(.*\)
            //public java.lang.String com.gupaoedu.vip.spring.demo.service.impl.ModifyService.add(java.lang.String,java.lang.String)
            Matcher matcher = pattern.matcher(m.toString());
            if(matcher.matches()){
                //能满足切面规则的类，添加的AOP配置中
                config.put(m,aspectClass.newInstance(),new Method[]{aspectClass.getMethod(before[1]),aspectClass.getMethod(after[1])});
            }
        }

        return  config;
    }

    //依赖注入，从这里开始，通过读取BeanDefinition中的信息
    //然后，通过反射机制创建一个实例并返回
    //Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
    //装饰器模式：
    //1、保留原来的OOP关系
    //2、我需要对它进行扩展，增强（为了以后AOP打基础）
    public Object getBean(String beanName) {
        CCBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        String className = beanDefinition.getBeanClassName();
        try{
            Object instance = instantionBean(beanDefinition);
            if(null == instance){ return  null;}
            CCBeanWrapper beanWrapper = new CCBeanWrapper(instance);
            beanWrapper.setAopConfig(instantionAopConfig(beanDefinition));
            this.beanWrapperMap.put(beanName,beanWrapper);
            //通过这样一调用，相当于给我们自己留有了可操作的空间
            return this.beanWrapperMap.get(beanName).getWrappedInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //传一个BeanDefinition，就返回一个实例Bean
    private Object instantionBean(CCBeanDefinition beanDefinition){
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try{
            //因为根据Class才能确定一个类是否有实例
            if(this.beanCacheMap.containsKey(className)){
                instance = this.beanCacheMap.get(className);
            }else{
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.beanCacheMap.put(className,instance);
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void populateBean(String beanName,Object instance) {

        Class clazz = instance.getClass();
        if (!(clazz.isAnnotationPresent(CCController.class) ||
                clazz.isAnnotationPresent(CCService.class))) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(CCAutowired.class)) {
                continue;
            }
            CCAutowired autowired = field.getAnnotation(CCAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }
            field.setAccessible(true);
            try {

                //System.out.println("=======================" +instance +"," + autowiredBeanName + "," + this.beanWrapperMap.get(autowiredBeanName));
                field.set(instance, this.beanWrapperMap.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
