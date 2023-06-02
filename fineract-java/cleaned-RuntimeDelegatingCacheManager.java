
package org.apache.fineract.infrastructure.cache.service;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.fineract.infrastructure.cache.CacheApiConstants;
import org.apache.fineract.infrastructure.cache.CacheEnumerations;
import org.apache.fineract.infrastructure.cache.data.CacheData;
import org.apache.fineract.infrastructure.cache.domain.CacheType;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.stereotype.Component;
@Component(value = "runtimeDelegatingCacheManager")
public class RuntimeDelegatingCacheManager implements CacheManager {
    private static final Logger LOG = LoggerFactory.getLogger(RuntimeDelegatingCacheManager.class);
    private final CacheManager cacheManager;
    private final CacheManager noOpCacheManager = new NoOpCacheManager();
    private CacheManager currentCacheManager;
    @Autowired
    public RuntimeDelegatingCacheManager(final JCacheCacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.currentCacheManager = this.noOpCacheManager;
    }
    @Override
    public Cache getCache(final String name) {
        return this.currentCacheManager.getCache(name);
    }
    @Override
    public Collection<String> getCacheNames() {
        return this.currentCacheManager.getCacheNames();
    }
    public Collection<CacheData> retrieveAll() {
        final boolean noCacheEnabled = this.currentCacheManager instanceof NoOpCacheManager;
        final boolean ehcacheEnabled = this.currentCacheManager instanceof JCacheCacheManager;
        final EnumOptionData noCacheType = CacheEnumerations.cacheType(CacheType.NO_CACHE);
        final EnumOptionData singleNodeCacheType = CacheEnumerations.cacheType(CacheType.SINGLE_NODE);
        final CacheData noCache = CacheData.instance(noCacheType, noCacheEnabled);
        final CacheData singleNodeCache = CacheData.instance(singleNodeCacheType, ehcacheEnabled);
        final Collection<CacheData> caches = Arrays.asList(noCache, singleNodeCache);
        return caches;
    }
    public Map<String, Object> switchToCache(final boolean ehcacheEnabled, final CacheType toCacheType) {
        final Map<String, Object> changes = new HashMap<>();
        final boolean noCacheEnabled = !ehcacheEnabled;
        final boolean distributedCacheEnabled = !ehcacheEnabled;
        switch (toCacheType) {
            case INVALID:
            break;
            case NO_CACHE:
                if (!noCacheEnabled) {
                    changes.put(CacheApiConstants.cacheTypeParameter, toCacheType.getValue());
                }
                this.currentCacheManager = this.noOpCacheManager;
            break;
            case SINGLE_NODE:
                if (!ehcacheEnabled) {
                    changes.put(CacheApiConstants.cacheTypeParameter, toCacheType.getValue());
                    clearEhCache();
                }
                this.currentCacheManager = this.cacheManager;
                if (this.currentCacheManager.getCacheNames().size() == 0) {
                    LOG.error("No caches configured for activated CacheManager {}", this.currentCacheManager);
                }
            break;
            case MULTI_NODE:
                if (!distributedCacheEnabled) {
                    changes.put(CacheApiConstants.cacheTypeParameter, toCacheType.getValue());
                }
            break;
        }
        return changes;
    }
    private void clearEhCache() {
        Iterable<String> cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            cacheManager.getCache(cacheName).clear();
        }
    }
}
