
package org.apache.fineract.commands.data;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
public final class AuditData {
    private final Long id;
    private final String actionName;
    private final String entityName;
    private final Long resourceId;
    private final Long subresourceId;
    private final String maker;
    private final ZonedDateTime madeOnDate;
    private final String checker;
    private final ZonedDateTime checkedOnDate;
    private final String processingResult;
    @Setter
    private String commandAsJson;
    private final String officeName;
    private final String groupLevelName;
    private final String groupName;
    private final String clientName;
    private final String loanAccountNo;
    private final String savingsAccountNo;
    private final Long clientId;
    private final Long loanId;
    private final String url;
}
