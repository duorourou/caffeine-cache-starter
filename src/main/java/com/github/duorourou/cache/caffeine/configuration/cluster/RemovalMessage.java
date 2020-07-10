package com.github.duorourou.cache.caffeine.configuration.cluster;

import lombok.Value;

@Value
public class RemovalMessage {
    /**
     * service name
     */
    String serviceName;
    /**
     * instance id. which can help us to known if a message from same instance
     */
    String instanceId;
    /**
     * the key which has been removed
     */
    String cacheKey;
    /**
     * the cache name which removal occurred
     */
    String cacheName;
}
