
package org.apache.fineract.infrastructure.security.service;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.useradministration.domain.AppUser;
public interface PlatformSecurityContext {
    AppUser authenticatedUser();
    AppUser getAuthenticatedUserIfPresent();
    void validateAccessRights(String resourceOfficeHierarchy);
    String officeHierarchy();
    boolean doesPasswordHasToBeRenewed(AppUser currentUser);
    AppUser authenticatedUser(CommandWrapper commandWrapper);
}
