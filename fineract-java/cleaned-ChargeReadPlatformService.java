
package org.apache.fineract.portfolio.charge.service;
import java.util.Collection;
import org.apache.fineract.portfolio.charge.data.ChargeData;
import org.apache.fineract.portfolio.charge.domain.ChargeTimeType;
public interface ChargeReadPlatformService {
    Collection<ChargeData> retrieveAllCharges();
    Collection<ChargeData> retrieveAllChargesForCurrency(String currencyCode);
    ChargeData retrieveCharge(Long chargeId);
    ChargeData retrieveNewChargeDetails();
    Collection<ChargeData> retrieveAllChargesApplicableToClients();
    Collection<ChargeData> retrieveLoanApplicableFees();
    Collection<ChargeData> retrieveLoanAccountApplicableCharges(Long loanId, ChargeTimeType[] excludeChargeTimes);
    Collection<ChargeData> retrieveLoanProductApplicableCharges(Long loanProductId, ChargeTimeType[] excludeChargeTimes);
    Collection<ChargeData> retrieveLoanApplicablePenalties();
    Collection<ChargeData> retrieveLoanProductCharges(Long loanProductId);
    Collection<ChargeData> retrieveLoanProductCharges(Long loanProductId, ChargeTimeType chargeTime);
    Collection<ChargeData> retrieveSavingsProductApplicableCharges(boolean feeChargesOnly);
    Collection<ChargeData> retrieveSavingsApplicablePenalties();
    Collection<ChargeData> retrieveSavingsProductCharges(Long savingsProductId);
    Collection<ChargeData> retrieveSavingsAccountApplicableCharges(Long savingsId);
    Collection<ChargeData> retrieveSharesApplicableCharges();
    Collection<ChargeData> retrieveShareProductCharges(Long shareProductId);
}
