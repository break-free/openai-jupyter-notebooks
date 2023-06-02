
package org.apache.fineract.infrastructure.cache.service;
import java.util.Map;
import org.apache.fineract.infrastructure.cache.domain.CacheType;
public interface CacheWritePlatformService {
    Map<String, Object> switchToCache(CacheType cacheType);
}
