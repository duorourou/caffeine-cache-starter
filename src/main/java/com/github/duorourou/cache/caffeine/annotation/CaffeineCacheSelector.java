package com.github.duorourou.cache.caffeine.annotation;

import com.github.duorourou.cache.caffeine.configuration.CustomCaffeineCacheConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Properties;

public class CaffeineCacheSelector implements ImportSelector {

    public static final String SPRING_CACHE_TYPE = "spring.cache.type";
    public static final String CAFFEINE = "caffeine";

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        boolean enabled = annotationMetadata.hasAnnotation(EnableCaffeineCache.class.getName());
        if (enabled) {
            Properties props = new Properties();
            props.put(SPRING_CACHE_TYPE, CAFFEINE);
            System.setProperties(props);
            return new String[]{CustomCaffeineCacheConfiguration.class.getName()};
        }
        return new String[0];
    }

}
