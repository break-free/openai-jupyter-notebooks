
package org.apache.fineract.commands.service;
import java.util.Collection;
import org.apache.fineract.commands.data.AuditData;
import org.apache.fineract.commands.data.AuditSearchData;
import org.apache.fineract.infrastructure.core.data.PaginationParameters;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.security.utils.SQLBuilder;
public interface AuditReadPlatformService {
    Collection<AuditData> retrieveAuditEntries(SQLBuilder extraCriteria, boolean includeJson);
    Page<AuditData> retrievePaginatedAuditEntries(SQLBuilder extraCriteria, boolean includeJson, PaginationParameters parameters);
    Collection<AuditData> retrieveAllEntriesToBeChecked(SQLBuilder extraCriteria, boolean includeJson);
    AuditData retrieveAuditEntry(Long auditId);
    AuditSearchData retrieveSearchTemplate(String useType);
}
