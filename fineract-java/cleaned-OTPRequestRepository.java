
package org.apache.fineract.infrastructure.security.domain;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fineract.infrastructure.security.data.OTPRequest;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@Repository
@ConditionalOnProperty("fineract.security.2fa.enabled")
@SuppressWarnings({ "MemberName" })
public class OTPRequestRepository {
    private final ConcurrentHashMap<Long, OTPRequest> OTPrequests = new ConcurrentHashMap<>();
    public OTPRequest getOTPRequestForUser(AppUser user) {
        Assert.notNull(user, "User must not be null");
        return this.OTPrequests.get(user.getId());
    }
    public void addOTPRequest(AppUser user, OTPRequest request) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(request, "Request must not be null");
        this.OTPrequests.put(user.getId(), request);
    }
    public void deleteOTPRequestForUser(AppUser user) {
        this.OTPrequests.remove(user.getId());
    }
}
