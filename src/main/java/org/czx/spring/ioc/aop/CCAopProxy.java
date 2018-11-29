package org.czx.spring.ioc.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Tom on 2018/5/2.
 */
//默认就用JDK动态代理
public class CCAopProxy implements InvocationHandler{

    private CCAopConfig config;
    private Object target;

    //把原生的对象传进来
    public Object getProxy(Object instance){
        this.target = instance;
        Class<?> clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public void setConfig(CCAopConfig config){
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());

        //在原始方法调用以前要执行增强的代码
        //这里需要通过原生方法去找，通过代理方法去Map中是找不到的
        if(config.contains(m)){
            CCAopConfig.GPAspect aspect = config.get(m);
           aspect.getPoints()[0].invoke(aspect.getAspect());
        }

        //反射调用原始的方法
        Object obj = method.invoke(this.target, args);
        System.out.println(args);

        //在原始方法调用以后要执行增强的代码
        if(config.contains(m)){
            CCAopConfig.GPAspect aspect = config.get(m);
            aspect.getPoints()[1].invoke(aspect.getAspect());
        }
        //将最原始的返回值返回出去
        return obj;
    }
}
