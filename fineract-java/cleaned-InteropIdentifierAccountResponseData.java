
package org.apache.fineract.interoperation.data;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public class InteropIdentifierAccountResponseData extends CommandProcessingResult {
    @NotEmpty
    private String accountId;
    protected InteropIdentifierAccountResponseData(Long resourceId, Long officeId, Long commandId, Map<String, Object> changesOnly,
            @NotNull String accountId) {
        super(resourceId, officeId, commandId, changesOnly);
        this.accountId = accountId;
    }
    protected static InteropIdentifierAccountResponseData build(Long resourceId, Long officeId, Long commandId,
            Map<String, Object> changesOnly, @NotNull String accountId) {
        return new InteropIdentifierAccountResponseData(resourceId, officeId, commandId, changesOnly, accountId);
    }
    public static InteropIdentifierAccountResponseData build(Long resourceId, @NotNull String accountId) {
        return build(resourceId, null, null, null, accountId);
    }
    public static InteropIdentifierAccountResponseData empty() {
        return build(null, null);
    }
    @NotNull
    public String getAccountId() {
        return accountId;
    }
    protected void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
