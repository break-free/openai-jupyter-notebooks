
package org.apache.fineract.infrastructure.security.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.security.data.OTPDeliveryMethod;
import org.apache.fineract.infrastructure.security.data.OTPRequest;
import org.apache.fineract.infrastructure.security.domain.TFAccessToken;
import org.apache.fineract.useradministration.domain.AppUser;
public interface TwoFactorService {
    List<OTPDeliveryMethod> getDeliveryMethodsForUser(AppUser user);
    OTPRequest createNewOTPToken(AppUser user, String deliveryMethodName, boolean extendedAccessToken);
    TFAccessToken createAccessTokenFromOTP(AppUser user, String otpToken);
    void validateTwoFactorAccessToken(AppUser user, String token);
    TFAccessToken fetchAccessTokenForUser(AppUser user, String token);
    TFAccessToken invalidateAccessToken(AppUser user, JsonCommand command);
}
