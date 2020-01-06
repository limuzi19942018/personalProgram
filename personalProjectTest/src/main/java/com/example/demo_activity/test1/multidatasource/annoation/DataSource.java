package com.example.demo_activity.test1.multidatasource.annoation;

import java.lang.annotation.*;

/**
 * @Author: yongl
 * @DATE: 2020/1/2 17:31
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface DataSource {

    String name() default "";
}
