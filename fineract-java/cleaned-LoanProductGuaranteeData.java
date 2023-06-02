
package org.apache.fineract.portfolio.loanproduct.data;
import java.io.Serializable;
import java.math.BigDecimal;
public final class LoanProductGuaranteeData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final Long productId;
    @SuppressWarnings("unused")
    private final BigDecimal mandatoryGuarantee;
    @SuppressWarnings("unused")
    private final BigDecimal minimumGuaranteeFromOwnFunds;
    @SuppressWarnings("unused")
    private final BigDecimal minimumGuaranteeFromGuarantor;
    public static LoanProductGuaranteeData instance(final Long id, final Long productId, final BigDecimal mandatoryGuarantee,
            final BigDecimal minimumGuaranteeFromOwnFunds, final BigDecimal minimumGuaranteeFromGuarantor) {
        return new LoanProductGuaranteeData(id, productId, mandatoryGuarantee, minimumGuaranteeFromOwnFunds, minimumGuaranteeFromGuarantor);
    }
    public static LoanProductGuaranteeData sensibleDefaultsForNewLoanProductCreation() {
        return new LoanProductGuaranteeData(null, null, null, null, null);
    }
    private LoanProductGuaranteeData(final Long id, final Long productId, final BigDecimal mandatoryGuarantee,
            final BigDecimal minimumGuaranteeFromOwnFunds, final BigDecimal minimumGuaranteeFromGuarantor) {
        this.id = id;
        this.productId = productId;
        this.mandatoryGuarantee = mandatoryGuarantee;
        this.minimumGuaranteeFromGuarantor = minimumGuaranteeFromGuarantor;
        this.minimumGuaranteeFromOwnFunds = minimumGuaranteeFromOwnFunds;
    }
}
