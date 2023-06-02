
package org.apache.fineract.infrastructure.security.data;
import java.time.ZonedDateTime;
public class AccessTokenData {
    private final String token;
    private final ZonedDateTime validFrom;
    private final ZonedDateTime validTo;
    public AccessTokenData(String token, ZonedDateTime validFrom, ZonedDateTime validTo) {
        this.token = token;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    public String getToken() {
        return token;
    }
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }
    public ZonedDateTime getValidTo() {
        return validTo;
    }
}
