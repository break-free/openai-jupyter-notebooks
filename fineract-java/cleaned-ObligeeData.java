
package org.apache.fineract.portfolio.loanaccount.guarantor.data;
import java.math.BigDecimal;
@SuppressWarnings("unused")
public final class ObligeeData {
    private final String firstName;
    private final String lastName;
    private final String displayName;
    private final String accountNumber;
    private final BigDecimal loanAmount;
    private final BigDecimal guaranteeAmount;
    private final BigDecimal amountReleased;
    private final BigDecimal amountTransferred;
    private ObligeeData(String firstName, String lastName, String displayName, String accountNumber, BigDecimal loanAmount,
            BigDecimal guaranteeAmount, BigDecimal amountReleased, BigDecimal amountTransferred) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.accountNumber = accountNumber;
        this.loanAmount = loanAmount;
        this.guaranteeAmount = guaranteeAmount;
        this.amountReleased = amountReleased;
        this.amountTransferred = amountTransferred;
    }
    public static ObligeeData instance(final String firstName, final String lastName, final String displayName, final String accountNumber,
            final BigDecimal loanAmount, final BigDecimal guaranteeAmount, final BigDecimal amountReleased,
            final BigDecimal amountTransferred) {
        return new ObligeeData(firstName, lastName, displayName, accountNumber, loanAmount, guaranteeAmount, amountReleased,
                amountTransferred);
    }
}
