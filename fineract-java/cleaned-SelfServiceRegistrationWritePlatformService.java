
package org.apache.fineract.portfolio.self.registration.service;
import org.apache.fineract.portfolio.self.registration.domain.SelfServiceRegistration;
import org.apache.fineract.useradministration.domain.AppUser;
public interface SelfServiceRegistrationWritePlatformService {
    SelfServiceRegistration createRegistrationRequest(String apiRequestBodyAsJson);
    AppUser createUser(String apiRequestBodyAsJson);
}
