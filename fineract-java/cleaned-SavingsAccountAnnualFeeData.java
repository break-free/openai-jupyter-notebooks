
package org.apache.fineract.portfolio.savings.data;
import java.time.LocalDate;
public final class SavingsAccountAnnualFeeData {
    private final Long id;
    private final Long accountId;
    private final String accountNo;
    private final LocalDate nextAnnualFeeDueDate;
    public static SavingsAccountAnnualFeeData instance(final Long id, final Long accountId, final String accountNo,
            final LocalDate nextAnnualFeeDueDate) {
        return new SavingsAccountAnnualFeeData(id, accountId, accountNo, nextAnnualFeeDueDate);
    }
    private SavingsAccountAnnualFeeData(final Long id, final Long accountId, final String accountNo, final LocalDate nextAnnualFeeDueDate) {
        this.id = id;
        this.accountId = accountId;
        this.accountNo = accountNo;
        this.nextAnnualFeeDueDate = nextAnnualFeeDueDate;
    }
    public Long getId() {
        return this.id;
    }
    public Long getAccountId() {
        return this.accountId;
    }
    public LocalDate getNextAnnualFeeDueDate() {
        return this.nextAnnualFeeDueDate;
    }
    public String getAccountNo() {
        return this.accountNo;
    }
}
