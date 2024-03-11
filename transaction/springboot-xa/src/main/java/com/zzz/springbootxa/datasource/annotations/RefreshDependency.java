package com.zzz.springbootxa.datasource.annotations;

import java.lang.annotation.*;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description: 自动刷新依赖注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RefreshDependency {
}
