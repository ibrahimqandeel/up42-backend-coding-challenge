package com.up42.codingchallenge.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class CacheComponent {

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    @CacheEvict(value = "features")
    public void evictFeaturesCacheEveryDay() {
        System.out.println("Features cache evicted");
    }
}
