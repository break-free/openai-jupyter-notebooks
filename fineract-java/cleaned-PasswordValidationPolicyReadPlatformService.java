
package org.apache.fineract.useradministration.service;
import java.util.Collection;
import org.apache.fineract.useradministration.data.PasswordValidationPolicyData;
public interface PasswordValidationPolicyReadPlatformService {
    Collection<PasswordValidationPolicyData> retrieveAll();
    PasswordValidationPolicyData retrieveActiveValidationPolicy();
}
