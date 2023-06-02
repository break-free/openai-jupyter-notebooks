
package org.apache.fineract.infrastructure.security.data;
import java.time.ZonedDateTime;
public class OTPMetadata {
    private final ZonedDateTime requestTime;
    private final int tokenLiveTimeInSec;
    private final boolean extendedAccessToken;
    private final OTPDeliveryMethod deliveryMethod;
    public OTPMetadata(ZonedDateTime requestTime, int tokenLiveTimeInSec, boolean extendedAccessToken, OTPDeliveryMethod deliveryMethod) {
        this.requestTime = requestTime;
        this.tokenLiveTimeInSec = tokenLiveTimeInSec;
        this.extendedAccessToken = extendedAccessToken;
        this.deliveryMethod = deliveryMethod;
    }
    public ZonedDateTime getRequestTime() {
        return requestTime;
    }
    public int getTokenLiveTimeInSec() {
        return tokenLiveTimeInSec;
    }
    public boolean isExtendedAccessToken() {
        return extendedAccessToken;
    }
    public OTPDeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }
}
