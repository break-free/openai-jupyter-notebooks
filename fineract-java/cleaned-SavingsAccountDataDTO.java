
package org.apache.fineract.portfolio.savings.data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.useradministration.domain.AppUser;
public class SavingsAccountDataDTO {
    private final Client client;
    private final Group group;
    private final Long savingsProductId;
    private final LocalDate applicationDate;
    private final AppUser appliedBy;
    private final DateTimeFormatter fmt;
    public SavingsAccountDataDTO(final Client client, final Group group, final Long savingsProductId, final LocalDate applicationDate,
            final AppUser appliedBy, final DateTimeFormatter fmt) {
        this.client = client;
        this.group = group;
        this.savingsProductId = savingsProductId;
        this.applicationDate = applicationDate;
        this.appliedBy = appliedBy;
        this.fmt = fmt;
    }
    public Client getClient() {
        return this.client;
    }
    public Group getGroup() {
        return this.group;
    }
    public Long getSavingsProduct() {
        return this.savingsProductId;
    }
    public LocalDate getApplicationDate() {
        return this.applicationDate;
    }
    public AppUser getAppliedBy() {
        return this.appliedBy;
    }
    public DateTimeFormatter getFmt() {
        return this.fmt;
    }
}
