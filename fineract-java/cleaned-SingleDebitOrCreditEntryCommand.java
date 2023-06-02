
package org.apache.fineract.accounting.journalentry.command;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class SingleDebitOrCreditEntryCommand {
    private final Long glAccountId;
    private final BigDecimal amount;
    private final String comments;
    private final Set<String> parametersPassedInRequest;
    public boolean isGlAccountIdChanged() {
        return this.parametersPassedInRequest.contains("glAccountId");
    }
    public boolean isGlAmountChanged() {
        return this.parametersPassedInRequest.contains("amount");
    }
    public boolean isCommentsChanged() {
        return this.parametersPassedInRequest.contains("comments");
    }
}
