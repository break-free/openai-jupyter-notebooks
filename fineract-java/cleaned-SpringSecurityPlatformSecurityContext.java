
package org.apache.fineract.infrastructure.security.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.commands.domain.CommandWrapper;
import org.apache.fineract.commands.service.CommandWrapperBuilder;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.security.exception.NoAuthorizationException;
import org.apache.fineract.infrastructure.security.exception.ResetPasswordException;
import org.apache.fineract.useradministration.domain.AppUser;
import org.apache.fineract.useradministration.exception.UnAuthenticatedUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class SpringSecurityPlatformSecurityContext implements PlatformSecurityContext {
    private final ConfigurationDomainService configurationDomainService;
    protected static final List<CommandWrapper> EXEMPT_FROM_PASSWORD_RESET_CHECK = new ArrayList<CommandWrapper>(
            List.of(new CommandWrapperBuilder().updateUser(null).build()));
    @Autowired
    SpringSecurityPlatformSecurityContext(final ConfigurationDomainService configurationDomainService) {
        this.configurationDomainService = configurationDomainService;
    }
    @Override
    public AppUser authenticatedUser() {
        AppUser currentUser = null;
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth != null) {
                currentUser = (AppUser) auth.getPrincipal();
            }
        }
        if (currentUser == null) {
            throw new UnAuthenticatedUserException();
        }
        if (this.doesPasswordHasToBeRenewed(currentUser)) {
            throw new ResetPasswordException(currentUser.getId());
        }
        return currentUser;
    }
    @Override
    public AppUser getAuthenticatedUserIfPresent() {
        AppUser currentUser = null;
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth != null) {
                currentUser = (AppUser) auth.getPrincipal();
            }
        }
        if (currentUser == null) {
            return null;
        }
        if (this.doesPasswordHasToBeRenewed(currentUser)) {
            throw new ResetPasswordException(currentUser.getId());
        }
        return currentUser;
    }
    @Override
    public AppUser authenticatedUser(CommandWrapper commandWrapper) {
        AppUser currentUser = null;
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            final Authentication auth = context.getAuthentication();
            if (auth != null) {
                currentUser = (AppUser) auth.getPrincipal();
            }
        }
        if (currentUser == null) {
            throw new UnAuthenticatedUserException();
        }
        if (this.shouldCheckForPasswordForceReset(commandWrapper) && this.doesPasswordHasToBeRenewed(currentUser)) {
            throw new ResetPasswordException(currentUser.getId());
        }
        return currentUser;
    }
    @Override
    public void validateAccessRights(final String resourceOfficeHierarchy) {
        final AppUser user = authenticatedUser();
        final String userOfficeHierarchy = user.getOffice().getHierarchy();
        if (!resourceOfficeHierarchy.startsWith(userOfficeHierarchy)) {
            throw new NoAuthorizationException("The user doesn't have enough permissions to access the resource.");
        }
    }
    @Override
    public String officeHierarchy() {
        return authenticatedUser().getOffice().getHierarchy();
    }
    @Override
    public boolean doesPasswordHasToBeRenewed(AppUser currentUser) {
        if (this.configurationDomainService.isPasswordForcedResetEnable() && !currentUser.getPasswordNeverExpires()) {
            Long passwordDurationDays = this.configurationDomainService.retrievePasswordLiveTime();
            final LocalDate passWordLastUpdateDate = currentUser.getLastTimePasswordUpdated();
            final LocalDate passwordExpirationDate = passWordLastUpdateDate.plusDays(passwordDurationDays);
            if (DateUtils.getLocalDateOfTenant().isAfter(passwordExpirationDate)) {
                return true;
            }
        }
        return false;
    }
    private boolean shouldCheckForPasswordForceReset(CommandWrapper commandWrapper) {
        for (CommandWrapper commandItem : EXEMPT_FROM_PASSWORD_RESET_CHECK) {
            if (commandItem.actionName().equals(commandWrapper.actionName())
                    && commandItem.getEntityName().equals(commandWrapper.getEntityName())) {
                return false;
            }
        }
        return true;
    }
}
