
package org.apache.fineract.useradministration.service;
import java.util.Collection;
import org.apache.fineract.useradministration.data.AppUserData;
public interface AppUserReadPlatformService {
    Collection<AppUserData> retrieveAllUsers();
    Collection<AppUserData> retrieveSearchTemplate();
    AppUserData retrieveNewUserDetails();
    AppUserData retrieveUser(Long userId);
    boolean isUsernameExist(String username);
}
