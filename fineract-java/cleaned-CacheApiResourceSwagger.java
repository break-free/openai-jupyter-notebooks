
package org.apache.fineract.infrastructure.cache.api;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
final class CacheApiResourceSwagger {
    private CacheApiResourceSwagger() {
    }
    @Schema(description = "GetCachesResponse")
    public static final class GetCachesResponse {
        private GetCachesResponse() {
        }
        public EnumOptionData cacheType;
        public boolean enabled;
    }
    @Schema(description = "PutCachesRequest")
    public static final class PutCachesRequest {
        private PutCachesRequest() {
        }
        @Schema(example = "2")
        public Long cacheType;
    }
    @Schema(description = "PutCachesResponse")
    public static final class PutCachesResponse {
        private PutCachesResponse() {
        }
        public static final class PutCachechangesSwagger {
            private PutCachechangesSwagger() {
            }
            @Schema(example = "2")
            public Long cacheType;
        }
        public PutCachechangesSwagger cacheType;
    }
}
