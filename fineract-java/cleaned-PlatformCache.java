
package org.apache.fineract.infrastructure.cache.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "c_cache")
public class PlatformCache extends AbstractPersistableCustom {
    @Column(name = "cache_type_enum")
    private Integer cacheType;
    protected PlatformCache() {
        this.cacheType = null;
    }
    public PlatformCache(final CacheType cacheType) {
        this.cacheType = cacheType.getValue();
    }
    public boolean isNoCachedEnabled() {
        return CacheType.fromInt(this.cacheType).isNoCache();
    }
    public boolean isEhcacheEnabled() {
        return CacheType.fromInt(this.cacheType).isEhcache();
    }
    public boolean isDistributedCacheEnabled() {
        return CacheType.fromInt(this.cacheType).isDistributedCache();
    }
    public void update(final CacheType cacheType) {
        this.cacheType = cacheType.getValue();
    }
}
