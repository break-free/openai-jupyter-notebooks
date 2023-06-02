
package org.apache.fineract.interoperation.data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.interoperation.domain.InteropIdentifier;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
public class InteropIdentifiersResponseData extends CommandProcessingResult {
    @NotNull
    private List<InteropIdentifierData> identifiers;
    protected InteropIdentifiersResponseData(Long resourceId, Long officeId, Long commandId, Map<String, Object> changesOnly,
            @NotNull List<InteropIdentifierData> identifiers) {
        super(resourceId, officeId, commandId, changesOnly);
        this.identifiers = identifiers;
    }
    protected InteropIdentifiersResponseData(@NotNull List<InteropIdentifierData> identifiers) {
        this(null, null, null, null, identifiers);
    }
    public static InteropIdentifiersResponseData build(SavingsAccount account) {
        List<InteropIdentifierData> result = new ArrayList<>();
        if (account != null) {
            for (InteropIdentifier identifier : account.getIdentifiers()) {
                result.add(InteropIdentifierData.build(identifier));
            }
        }
        return new InteropIdentifiersResponseData(result);
    }
}
