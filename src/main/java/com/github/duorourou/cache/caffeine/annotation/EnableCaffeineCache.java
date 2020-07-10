package com.github.duorourou.cache.caffeine.annotation;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(CaffeineCacheSelector.class)
@ComponentScan(basePackages = "com.github.duorourou.cache.caffeine")
public @interface EnableCaffeineCache {

}
