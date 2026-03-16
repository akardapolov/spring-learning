package com.example.hibernate.config;

import java.time.Duration;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EhcacheConfig {

  @Bean
  public CacheManager jCacheManager() {
    CachingProvider provider = Caching.getCachingProvider(
        "org.ehcache.jsr107.EhcacheCachingProvider"
    );

    CacheManager cacheManager = provider.getCacheManager();

    createCacheIfAbsent(cacheManager, "default-query-results-region", null, 1000);
    createCacheIfAbsent(cacheManager, "default-update-timestamps-region", null, 1000);
    createCacheIfAbsent(cacheManager, "product-cache", Duration.ofMinutes(30), 1000);
    createCacheIfAbsent(cacheManager, "category-cache", Duration.ofMinutes(30), 1000);

    return cacheManager;
  }

  private void createCacheIfAbsent(CacheManager cacheManager,
                                   String cacheName,
                                   Duration ttl,
                                   long heapEntries) {
    if (cacheManager.getCache(cacheName) != null) {
      return;
    }

    CacheConfigurationBuilder<Object, Object> builder = CacheConfigurationBuilder
        .newCacheConfigurationBuilder(
            Object.class,
            Object.class,
            ResourcePoolsBuilder.heap(heapEntries)
        );

    if (ttl != null) {
      builder = builder.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl));
    }

    cacheManager.createCache(
        cacheName,
        Eh107Configuration.fromEhcacheCacheConfiguration(builder.build())
    );
  }
}