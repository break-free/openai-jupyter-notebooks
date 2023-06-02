
package org.apache.fineract.portfolio.self.registration.service;
public interface SelfServiceRegistrationReadPlatformService {
    boolean isClientExist(String accountNumber, String firstName, String lastName, String mobileNumber, boolean isEmailAuthenticationMode);
}
