
package org.apache.fineract.organisation.teller.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.organisation.office.domain.Office;
@Entity
@Table(name = "m_cashier_transactions")
public class CashierTransaction extends AbstractPersistableCustom {
    @Transient
    private Office office;
    @Transient
    private Teller teller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier_id", nullable = false)
    private Cashier cashier;
    @Column(name = "txn_type", nullable = false)
    private Integer txnType;
    @Column(name = "txn_date", nullable = false)
    private LocalDate txnDate;
    @Column(name = "txn_amount", scale = 6, precision = 19, nullable = false)
    private BigDecimal txnAmount;
    @Column(name = "txn_note", nullable = true)
    private String txnNote;
    @Column(name = "entity_type", nullable = true)
    private String entityType;
    @Column(name = "entity_id", nullable = true)
    private Long entityId;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "currency_code", nullable = true)
    private String currencyCode;
    public CashierTransaction() {
    }
    public static CashierTransaction fromJson(final Cashier cashier, final JsonCommand command) {
        final Integer txnType = command.integerValueOfParameterNamed("txnType");
        final BigDecimal txnAmount = command.bigDecimalValueOfParameterNamed("txnAmount");
        final LocalDate txnDate = command.localDateValueOfParameterNamed("txnDate");
        final String entityType = command.stringValueOfParameterNamed("entityType");
        final String txnNote = command.stringValueOfParameterNamed("txnNote");
        final Long entityId = command.longValueOfParameterNamed("entityId");
        final String currencyCode = command.stringValueOfParameterNamed("currencyCode");
        return new CashierTransaction(cashier, txnType, txnAmount, txnDate, entityType, entityId, txnNote, currencyCode);
    }
    public CashierTransaction(Cashier cashier, Integer txnType, BigDecimal txnAmount, LocalDate txnDate, String entityType, Long entityId,
            String txnNote, String currencyCode) {
        this.cashier = cashier;
        this.txnType = txnType;
        if (txnDate != null) {
            this.txnDate = txnDate;
        }
        this.txnAmount = txnAmount;
        this.entityType = entityType;
        this.entityId = entityId;
        this.txnNote = txnNote;
        this.createdDate = DateUtils.getLocalDateTimeOfSystem();
        this.currencyCode = currencyCode;
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);
        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();
        final String txnTypeParamName = "txnType";
        if (command.isChangeInIntegerParameterNamed(txnTypeParamName, this.txnType)) {
            final Integer newValue = command.integerValueOfParameterNamed(txnTypeParamName);
            actualChanges.put(txnTypeParamName, newValue);
            this.txnType = newValue;
        }
        final String txnDateParamName = "txnDate";
        if (command.isChangeInLocalDateParameterNamed(txnDateParamName, getTxnLocalDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(txnDateParamName);
            actualChanges.put(txnDateParamName, valueAsInput);
            actualChanges.put("dateFormat", dateFormatAsInput);
            actualChanges.put("locale", localeAsInput);
            this.txnDate = command.localDateValueOfParameterNamed(txnDateParamName);
        }
        final String txnAmountParamName = "txnAmount";
        if (command.isChangeInBigDecimalParameterNamed(txnAmountParamName, this.txnAmount)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(txnAmountParamName);
            actualChanges.put(txnAmountParamName, newValue);
            this.txnAmount = newValue;
        }
        final String txnNoteParamName = "txnNote";
        if (command.isChangeInStringParameterNamed(txnNoteParamName, this.txnNote)) {
            final String newValue = command.stringValueOfParameterNamed(txnNoteParamName);
            actualChanges.put(txnNoteParamName, newValue);
            this.txnNote = newValue;
        }
        final String currencyCodeParamName = "currencyCode";
        if (command.isChangeInStringParameterNamed(currencyCodeParamName, this.currencyCode)) {
            final String newValue = command.stringValueOfParameterNamed(currencyCodeParamName);
            actualChanges.put(currencyCodeParamName, newValue);
            this.currencyCode = newValue;
        }
        return actualChanges;
    }
    public Office getOffice() {
        return office;
    }
    public void setOffice(Office office) {
        this.office = office;
    }
    public Teller getTeller() {
        return teller;
    }
    public void setTeller(Teller teller) {
        this.teller = teller;
    }
    public Integer getTxnType() {
        return txnType;
    }
    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }
    public LocalDate getTxnDate() {
        return txnDate;
    }
    public LocalDate getTxnLocalDate() {
        return this.txnDate;
    }
    public void setTxnDate(LocalDate txnDate) {
        this.txnDate = txnDate;
    }
    public String getEntityType() {
        return entityType;
    }
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
    public Long getEntityId() {
        return entityId;
    }
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    public String getTxnNote() {
        return txnNote;
    }
    public BigDecimal getTxnAmount() {
        return txnAmount;
    }
    public void setTxnNote(String txnNote) {
        this.txnNote = txnNote;
    }
    public String getCurrencyCode() {
        return this.currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
