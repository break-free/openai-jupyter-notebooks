
package org.apache.fineract.infrastructure.security.service;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.security.data.OTPRequest;
import org.apache.fineract.useradministration.domain.AppUser;
public interface TwoFactorConfigurationService {
    Map<String, Object> retrieveAll();
    boolean isSMSEnabled();
    Integer getSMSProviderId();
    String getSmsText();
    boolean isEmailEnabled();
    String getEmailSubject();
    String getEmailBody();
    String getFormattedEmailSubjectFor(AppUser user, OTPRequest request);
    String getFormattedEmailBodyFor(AppUser user, OTPRequest request);
    String getFormattedSmsTextFor(AppUser user, OTPRequest request);
    Integer getOTPTokenLength();
    Integer getOTPTokenLiveTime();
    Integer getAccessTokenLiveTime();
    Integer getAccessTokenExtendedLiveTime();
    Map<String, Object> update(JsonCommand command);
}
