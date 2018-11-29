package org.czx.spring.ioc.annotation;

import java.lang.annotation.*;

/**
 * Created by zhixuecai on 2018/11/26.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CCService {

     String value() default "";
}
