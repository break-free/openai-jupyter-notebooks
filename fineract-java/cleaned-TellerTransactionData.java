
package org.apache.fineract.organisation.teller.data;
import java.io.Serializable;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class TellerTransactionData implements Serializable {
    private final Long id;
    private final Long officeId;
    private final Long tellerId;
    private final Long cashierId;
    private final Long clientId;
    private final EnumOptionData type;
    private final Double amount;
    private final LocalDate postingDate;
    private TellerTransactionData(final Long id, final Long officeId, final Long tellerId, final Long cashierId, final Long clientId,
            final EnumOptionData type, final Double amount, final LocalDate postingDate) {
        this.id = id;
        this.officeId = officeId;
        this.tellerId = tellerId;
        this.cashierId = cashierId;
        this.clientId = clientId;
        this.type = type;
        this.amount = amount;
        this.postingDate = postingDate;
    }
    public static TellerTransactionData instance(final Long id, final Long officeId, final Long tellerId, final Long cashierId,
            final Long clientId, final EnumOptionData type, final Double amount, final LocalDate postingDate) {
        return new TellerTransactionData(id, officeId, tellerId, cashierId, clientId, type, amount, postingDate);
    }
    public Long getId() {
        return id;
    }
    public Long getOfficeId() {
        return officeId;
    }
    public Long getTellerId() {
        return tellerId;
    }
    public Long getCashierId() {
        return cashierId;
    }
    public Long getClientId() {
        return clientId;
    }
    public EnumOptionData getType() {
        return type;
    }
    public Double getAmount() {
        return amount;
    }
    public LocalDate getPostingDate() {
        return postingDate;
    }
}
