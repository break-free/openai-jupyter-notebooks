
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import org.apache.fineract.portfolio.charge.data.ChargeData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountAnnualFeeData;
import org.apache.fineract.portfolio.savings.data.SavingsAccountChargeData;
public interface SavingsAccountChargeReadPlatformService {
    ChargeData retrieveSavingsAccountChargeTemplate();
    Collection<SavingsAccountChargeData> retrieveSavingsAccountCharges(Long savingsAccountId, String status);
    SavingsAccountChargeData retrieveSavingsAccountChargeDetails(Long savingsAccountChargeId, Long savingsAccountId);
    Collection<SavingsAccountAnnualFeeData> retrieveChargesWithAnnualFeeDue();
    Collection<SavingsAccountAnnualFeeData> retrieveChargesWithDue();
}
