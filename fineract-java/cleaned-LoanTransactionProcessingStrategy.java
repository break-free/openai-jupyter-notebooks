
package org.apache.fineract.portfolio.loanproduct.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.loanproduct.data.TransactionProcessingStrategyData;
@Entity
@Table(name = "ref_loan_transaction_processing_strategy")
public class LoanTransactionProcessingStrategy extends AbstractPersistableCustom {
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "sort_order")
    private Integer sortOrder; 
    protected LoanTransactionProcessingStrategy() {
    }
    public TransactionProcessingStrategyData toData() {
        return new TransactionProcessingStrategyData(getId(), this.code, this.name);
    }
    public boolean isStandardStrategy() {
        return "mifos-standard-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isHeavensfamilyStrategy() {
        return "heavensfamily-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isEarlyPaymentStrategy() {
        return "early-repayment-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isCreocoreStrategy() {
        return "creocore-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isIndianRBIStrategy() {
        return "rbi-india-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isPrincipalInterestPenaltiesFeesOrderStrategy() {
        return "principal-interest-penalties-fees-order-strategy".equalsIgnoreCase(this.code);
    }
    public boolean isInterestPrincipalPenaltiesFeesOrderStrategy() {
        return "interest-principal-penalties-fees-order-strategy".equalsIgnoreCase(this.code);
    }
}
