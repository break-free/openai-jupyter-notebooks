
package org.apache.fineract.useradministration.domain;
import org.apache.fineract.useradministration.exception.UserNotFoundException;
import org.apache.fineract.useradministration.service.AppUserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AppUserRepositoryWrapper {
    private final AppUserRepository appUserRepository;
    @Autowired
    public AppUserRepositoryWrapper(final AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
    public AppUser fetchSystemUser() {
        AppUser user = this.appUserRepository.findAppUserByName(AppUserConstants.SYSTEM_USER_NAME);
        if (user == null) {
            throw new UserNotFoundException(AppUserConstants.SYSTEM_USER_NAME);
        }
        return user;
    }
}
