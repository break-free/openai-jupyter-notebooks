
package org.apache.fineract.infrastructure.security.data;
import java.time.ZonedDateTime;
import org.apache.fineract.infrastructure.core.service.DateUtils;
public class OTPRequest {
    private final String token;
    private final OTPMetadata metadata;
    public OTPRequest(String token, OTPMetadata metadata) {
        this.token = token;
        this.metadata = metadata;
    }
    public static OTPRequest create(String token, int tokenLiveTimeInSec, boolean extendedAccessToken, OTPDeliveryMethod deliveryMethod) {
        final OTPMetadata metadata = new OTPMetadata(DateUtils.getLocalDateTimeOfTenant().atZone(DateUtils.getDateTimeZoneOfTenant()),
                tokenLiveTimeInSec, extendedAccessToken, deliveryMethod);
        return new OTPRequest(token, metadata);
    }
    public String getToken() {
        return token;
    }
    public OTPMetadata getMetadata() {
        return metadata;
    }
    public boolean isValid() {
        ZonedDateTime expireTime = metadata.getRequestTime().plusSeconds(metadata.getTokenLiveTimeInSec());
        return ZonedDateTime.now(DateUtils.getDateTimeZoneOfTenant()).isBefore(expireTime);
    }
}
