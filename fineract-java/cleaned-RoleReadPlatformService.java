
package org.apache.fineract.useradministration.service;
import java.util.Collection;
import org.apache.fineract.useradministration.data.RoleData;
public interface RoleReadPlatformService {
    Collection<RoleData> retrieveAll();
    Collection<RoleData> retrieveAllActiveRoles();
    Collection<RoleData> retrieveAllSelfServiceRoles();
    RoleData retrieveOne(Long roleId);
    Collection<RoleData> retrieveAppUserRoles(Long appUserId);
}
