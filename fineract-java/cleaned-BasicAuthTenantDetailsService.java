
package org.apache.fineract.infrastructure.security.service;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
public interface BasicAuthTenantDetailsService {
    FineractPlatformTenant loadTenantById(String tenantId, boolean isReport);
}
