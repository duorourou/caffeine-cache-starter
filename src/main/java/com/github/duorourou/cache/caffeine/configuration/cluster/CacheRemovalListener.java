package com.github.duorourou.cache.caffeine.configuration.cluster;

import com.github.benmanes.caffeine.cache.RemovalListener;
import org.springframework.stereotype.Component;

@Component
public interface CacheRemovalListener extends RemovalListener<Object, Object> {

}
