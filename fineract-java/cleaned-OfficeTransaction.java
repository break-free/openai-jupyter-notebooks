
package org.apache.fineract.organisation.office.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.monetary.domain.MonetaryCurrency;
import org.apache.fineract.organisation.monetary.domain.Money;
@Entity
@Table(name = "m_office_transaction")
public class OfficeTransaction extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_office_id")
    private Office from;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_office_id")
    private Office to;
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
    @Embedded
    private MonetaryCurrency currency;
    @Column(name = "transaction_amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal transactionAmount;
    @Column(name = "description", nullable = true, length = 100)
    private String description;
    public static OfficeTransaction fromJson(final Office fromOffice, final Office toOffice, final Money amount,
            final JsonCommand command) {
        final LocalDate transactionLocalDate = command.localDateValueOfParameterNamed("transactionDate");
        final String description = command.stringValueOfParameterNamed("description");
        return new OfficeTransaction(fromOffice, toOffice, transactionLocalDate, amount, description);
    }
    protected OfficeTransaction() {
        this.transactionDate = null;
    }
    private OfficeTransaction(final Office fromOffice, final Office toOffice, final LocalDate transactionLocalDate, final Money amount,
            final String description) {
        this.from = fromOffice;
        this.to = toOffice;
        if (transactionLocalDate != null) {
            this.transactionDate = transactionLocalDate;
        }
        this.currency = amount.getCurrency();
        this.transactionAmount = amount.getAmount();
        this.description = description;
    }
}
