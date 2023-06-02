
package org.apache.fineract.useradministration.service;
import java.util.Collection;
import org.apache.fineract.useradministration.data.PermissionData;
public interface PermissionReadPlatformService {
    Collection<PermissionData> retrieveAllPermissions();
    Collection<PermissionData> retrieveAllMakerCheckerablePermissions();
    Collection<PermissionData> retrieveAllRolePermissions(Long roleId);
}
