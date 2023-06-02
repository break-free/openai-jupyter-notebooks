
package org.apache.fineract.infrastructure.cache;
import org.apache.fineract.infrastructure.cache.service.RuntimeDelegatingCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@EnableCaching
public class PlatformCacheConfiguration extends CachingConfigurerSupport implements CachingConfigurer {
    @Autowired
    private RuntimeDelegatingCacheManager delegatingCacheManager;
    @Bean
    @Override
    public CacheManager cacheManager() {
        return this.delegatingCacheManager;
    }
}
