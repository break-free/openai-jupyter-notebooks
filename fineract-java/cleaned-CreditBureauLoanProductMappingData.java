
package org.apache.fineract.infrastructure.creditbureau.data;
public final class CreditBureauLoanProductMappingData {
    private final long creditbureauLoanProductMappingId;
    private final long organisationCreditBureauId;
    private final String alias;
    private final String creditbureauSummary;
    private final String loanProductName;
    private final long loanProductId;
    private final boolean isCreditCheckMandatory;
    private final boolean skipCrediCheckInFailure;
    private final long stalePeriod;
    private final boolean isActive;
    private CreditBureauLoanProductMappingData(final long creditbureauLoanProductMappingId, final long organisationCreditBureauId,
            final String alias, final String creditbureauSummary, final String loanProductName, final long loanProductId,
            final boolean isCreditCheckMandatory, final boolean skipCrediCheckInFailure, final long stalePeriod, final boolean isActive) {
        this.creditbureauLoanProductMappingId = creditbureauLoanProductMappingId;
        this.organisationCreditBureauId = organisationCreditBureauId;
        this.alias = alias;
        this.creditbureauSummary = creditbureauSummary;
        this.loanProductName = loanProductName;
        this.loanProductId = loanProductId;
        this.isCreditCheckMandatory = isCreditCheckMandatory;
        this.skipCrediCheckInFailure = skipCrediCheckInFailure;
        this.stalePeriod = stalePeriod;
        this.isActive = isActive;
    }
    public static CreditBureauLoanProductMappingData instance(final long creditbureauLoanProductMappingId,
            final long organisationCreditBureauId, final String alias, final String creditbureauSummary, final String loanProductName,
            final long loanProductId, final boolean isCreditCheckMandatory, final boolean skipCrediCheckInFailure, final long stalePeriod,
            final boolean isActive) {
        return new CreditBureauLoanProductMappingData(creditbureauLoanProductMappingId, organisationCreditBureauId, alias,
                creditbureauSummary, loanProductName, loanProductId, isCreditCheckMandatory, skipCrediCheckInFailure, stalePeriod,
                isActive);
    }
    public static CreditBureauLoanProductMappingData instance1(final String loanProductName, final long loanProductId) {
        return new CreditBureauLoanProductMappingData(0, 0, "", "", loanProductName, loanProductId, false, false, 0, false);
    }
    public long getCreditbureauLoanProductMappingId() {
        return this.creditbureauLoanProductMappingId;
    }
    public String getAlias() {
        return this.alias;
    }
    public String getCreditbureauSummary() {
        return this.creditbureauSummary;
    }
    public String getLoanProductName() {
        return this.loanProductName;
    }
    public long getOrganisationCreditBureauId() {
        return this.organisationCreditBureauId;
    }
    public long getLoanProductId() {
        return this.loanProductId;
    }
    public boolean isCreditCheckMandatory() {
        return this.isCreditCheckMandatory;
    }
    public boolean isSkipCrediCheckInFailure() {
        return this.skipCrediCheckInFailure;
    }
    public long getStalePeriod() {
        return this.stalePeriod;
    }
    public boolean isActive() {
        return this.isActive;
    }
}
