package com.github.duorourou.cache.caffeine.configuration.cluster;

public interface RemovalMessagePublisher {

    /**
     * removalMessage publisher
     * and need to set the implementation as a Spring Bean
     * @param channelName message published channel
     * @param message removal message
     */
    void publish(String channelName, RemovalMessage message);

}
