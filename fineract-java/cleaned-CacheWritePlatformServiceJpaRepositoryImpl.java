
package org.apache.fineract.infrastructure.cache.service;
import java.util.Map;
import org.apache.fineract.infrastructure.cache.domain.CacheType;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CacheWritePlatformServiceJpaRepositoryImpl implements CacheWritePlatformService {
    private final ConfigurationDomainService configurationDomainService;
    private final RuntimeDelegatingCacheManager cacheService;
    @Autowired
    public CacheWritePlatformServiceJpaRepositoryImpl(final ConfigurationDomainService configurationDomainService,
            @Qualifier("runtimeDelegatingCacheManager") final RuntimeDelegatingCacheManager cacheService) {
        this.configurationDomainService = configurationDomainService;
        this.cacheService = cacheService;
    }
    @Transactional
    @Override
    public Map<String, Object> switchToCache(final CacheType toCacheType) {
        final boolean ehCacheEnabled = this.configurationDomainService.isEhcacheEnabled();
        final Map<String, Object> changes = this.cacheService.switchToCache(ehCacheEnabled, toCacheType);
        if (!changes.isEmpty()) {
            this.configurationDomainService.updateCache(toCacheType);
        }
        return changes;
    }
}
