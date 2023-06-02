
package org.apache.fineract.infrastructure.core.service;
import org.apache.fineract.infrastructure.core.domain.EmailDetail;
public interface PlatformEmailService {
    void sendToUserAccount(String organisationName, String contactName, String address, String username, String unencodedPassword);
    void sendDefinedEmail(EmailDetail emailDetails);
}
