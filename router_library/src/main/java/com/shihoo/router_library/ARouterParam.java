package com.shihoo.router_library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shihoo ON 2018/12/6.
 * Email shihu.wang@bodyplus.cc 451082005@qq.com
 *
 * 路由携带的参数
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ARouterParam {

    String value();

    String name() default "shihoo";

    String age() default "18";
}
