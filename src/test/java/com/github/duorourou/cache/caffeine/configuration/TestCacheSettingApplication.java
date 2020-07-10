package com.github.duorourou.cache.caffeine.configuration;

import com.github.duorourou.cache.caffeine.configuration.cluster.RemovalMessagePublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
public class TestCacheSettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestCacheSettingApplication.class, args);
    }

    @Bean
    @Primary
    public RemovalMessagePublisher cacheRemovalListener() {
        return (c, m) -> System.out.println(c + m);
    }
}
