package org.czx.spring.ioc.beans;

/**
 * 生成对象完成靠的是一个类的定义
 * 考虑通过反射生成一个类的条件是需要知道类的位置
 * Created by zhixuecai on 2018/11/26.
 */
public class CCBeanDefinition {
    //所在文件的包路径
    private String beanClassName;
    //时候lazy-init
    private Boolean lazyInit=false;
    //注册式注册一个关系对应，作为map的key
    private String beanFactoryName;


    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public Boolean getLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(Boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getBeanFactoryName() {
        return beanFactoryName;
    }

    public void setBeanFactoryName(String beanFactoryName) {
        this.beanFactoryName = beanFactoryName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }
}
