
package org.apache.fineract.portfolio.collateral.domain;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.collateral.api.CollateralApiConstants.CollateralJSONinputParams;
import org.apache.fineract.portfolio.collateral.data.CollateralData;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
@Entity
@Table(name = "m_loan_collateral")
public class LoanCollateral extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
    @ManyToOne
    @JoinColumn(name = "type_cv_id", nullable = false)
    private CodeValue type;
    @Column(name = "value", scale = 6, precision = 19)
    private BigDecimal value;
    @Column(name = "description", length = 500)
    private String description;
    public static LoanCollateral from(final CodeValue collateralType, final BigDecimal value, final String description) {
        return new LoanCollateral(null, collateralType, value, description);
    }
    protected LoanCollateral() {
    }
    private LoanCollateral(final Loan loan, final CodeValue collateralType, final BigDecimal value, final String description) {
        this.loan = loan;
        this.type = collateralType;
        this.value = value;
        this.description = StringUtils.defaultIfEmpty(description, null);
    }
    public void assembleFrom(final CodeValue collateralType, final BigDecimal value, final String description) {
        this.type = collateralType;
        this.description = description;
        this.value = value;
    }
    public void associateWith(final Loan loan) {
        this.loan = loan;
    }
    public static LoanCollateral fromJson(final Loan loan, final CodeValue collateralType, final JsonCommand command) {
        final String description = command.stringValueOfParameterNamed(CollateralJSONinputParams.DESCRIPTION.getValue());
        final BigDecimal value = command.bigDecimalValueOfParameterNamed(CollateralJSONinputParams.VALUE.getValue());
        return new LoanCollateral(loan, collateralType, value, description);
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);
        final String collateralTypeIdParamName = CollateralJSONinputParams.COLLATERAL_TYPE_ID.getValue();
        if (command.isChangeInLongParameterNamed(collateralTypeIdParamName, this.type.getId())) {
            final Long newValue = command.longValueOfParameterNamed(collateralTypeIdParamName);
            actualChanges.put(collateralTypeIdParamName, newValue);
        }
        final String descriptionParamName = CollateralJSONinputParams.DESCRIPTION.getValue();
        if (command.isChangeInStringParameterNamed(descriptionParamName, this.description)) {
            final String newValue = command.stringValueOfParameterNamed(descriptionParamName);
            actualChanges.put(descriptionParamName, newValue);
            this.description = StringUtils.defaultIfEmpty(newValue, null);
        }
        final String valueParamName = CollateralJSONinputParams.VALUE.getValue();
        if (command.isChangeInBigDecimalParameterNamed(valueParamName, this.value)) {
            final BigDecimal newValue = command.bigDecimalValueOfParameterNamed(valueParamName);
            actualChanges.put(valueParamName, newValue);
            this.value = newValue;
        }
        return actualChanges;
    }
    public CollateralData toData() {
        final CodeValueData typeData = this.type.toData();
        return CollateralData.instance(getId(), typeData, this.value, this.description, null);
    }
    public void setCollateralType(final CodeValue type) {
        this.type = type;
    }
}
