
package org.apache.fineract.commands.data;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.useradministration.data.AppUserData;
@RequiredArgsConstructor
@Getter
public final class AuditSearchData {
    private final Collection<AppUserData> appUsers;
    private final List<String> actionNames;
    private final List<String> entityNames;
    private final Collection<ProcessingResultLookup> processingResults;
}
