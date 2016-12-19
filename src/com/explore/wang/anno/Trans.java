package com.explore.wang.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解， 标志此方法需要被动态代理类处理， 开启事务
 * Created by 王兆琦  on 2016/12/15 19.14.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trans {
}
