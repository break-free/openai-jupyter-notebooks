
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public final class PostDatedChecksData {
    private final Long id;
    private final Integer installmentId;
    private final String name;
    private final Long accountNo;
    private final BigDecimal amount;
    private final LocalDate installmentDate;
    private final Long checkNo;
    private final Integer status;
    private PostDatedChecksData(final LocalDate installmentDate, final Long id, final Integer installmentId, final Long accountNo,
            final BigDecimal amount, final String name, final Long checkNo, final Integer status) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.name = name;
        this.id = id;
        this.installmentDate = installmentDate;
        this.installmentId = installmentId;
        this.checkNo = checkNo;
        this.status = status;
    }
    public static PostDatedChecksData from(final LocalDate installmentDate, final Long id, final Integer installmentId,
            final Long accountNo, final BigDecimal amount, final String name, final Long checkNo, final Integer status) {
        return new PostDatedChecksData(installmentDate, id, installmentId, accountNo, amount, name, checkNo, status);
    }
}
