package com.wdnyjx.Utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * File ...
 *
 * @Project:AllInOne
 * @Package:com.wdnyjx.Utils
 * @author:OverLord
 * @Since:2020/6/16 13:13
 * @Version:v0.0.1
 */
@Configuration
public class CacheUtil {
    @Bean
    @Lazy
    @Qualifier("guava")
   public <K,V> Cache<K,V> getCache(){
        Cache<K, V> cache = CacheBuilder.newBuilder()
                .initialCapacity(2)
                .maximumSize(20)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
        return cache;
    }
}
