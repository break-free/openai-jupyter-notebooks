
package org.apache.fineract.portfolio.savings.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.savings.SavingsApiConstants;
import org.apache.fineract.portfolio.savings.data.SavingsAccountData;
import org.apache.fineract.portfolio.savings.data.SavingsProductData;
public class SavingsApiSetConstants extends SavingsApiConstants {
    protected static final Set<String> SAVINGS_PRODUCT_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(idParamName, nameParamName, shortNameParamName, descriptionParamName, "currency", digitsAfterDecimalParamName,
                    inMultiplesOfParamName, nominalAnnualInterestRateParamName, interestCompoundingPeriodTypeParamName,
                    interestPostingPeriodTypeParamName, interestCalculationTypeParamName, interestCalculationDaysInYearTypeParamName,
                    minRequiredOpeningBalanceParamName, lockinPeriodFrequencyParamName, lockinPeriodFrequencyTypeParamName,
                    withdrawalFeeAmountParamName, withdrawalFeeTypeParamName, withdrawalFeeForTransfersParamName, feeAmountParamName,
                    feeOnMonthDayParamName, "currencyOptions", "interestCompoundingPeriodTypeOptions", "interestPostingPeriodTypeOptions",
                    "interestCalculationTypeOptions", "interestCalculationDaysInYearTypeOptions", "lockinPeriodFrequencyTypeOptions",
                    "withdrawalFeeTypeOptions", nominalAnnualInterestRateOverdraftParamName, minOverdraftForInterestCalculationParamName,
                    withHoldTaxParamName, taxGroupIdParamName, isDormancyTrackingActiveParamName, daysToInactiveParamName,
                    daysToDormancyParamName, daysToInactiveParamName, accountMappingForPaymentParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName,
            accountNoParamName, externalIdParamName, statusParamName, activatedOnDateParamName, staffIdParamName, clientIdParamName,
            "clientName", groupIdParamName, "groupName", "savingsProductId", "savingsProductName", "currency",
            nominalAnnualInterestRateParamName, interestCompoundingPeriodTypeParamName, interestCalculationTypeParamName,
            interestCalculationDaysInYearTypeParamName, minRequiredOpeningBalanceParamName, lockinPeriodFrequencyParamName,
            lockinPeriodFrequencyTypeParamName, withdrawalFeeAmountParamName, withdrawalFeeTypeParamName,
            withdrawalFeeForTransfersParamName, feeAmountParamName, feeOnMonthDayParamName, "summary", "transactions", "productOptions",
            "interestCompoundingPeriodTypeOptions", "interestPostingPeriodTypeOptions", "interestCalculationTypeOptions",
            "interestCalculationDaysInYearTypeOptions", "lockinPeriodFrequencyTypeOptions", "withdrawalFeeTypeOptions", "withdrawalFee",
            "annualFee", onHoldFundsParamName, nominalAnnualInterestRateOverdraftParamName, minOverdraftForInterestCalculationParamName,
            datatables, savingsAmountOnHold, accountMappingForPaymentParamName, interestPostedTillDate));
    protected static final Set<String> SAVINGS_TRANSACTION_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(idParamName, "accountId", accountNoParamName, "currency", "amount", dateParamName, paymentDetailDataParamName,
                    runningBalanceParamName, reversedParamName, noteParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_CHARGES_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(chargeIdParamName, savingsAccountChargeIdParamName, chargeNameParamName, penaltyParamName,
                    chargeTimeTypeParamName, dueAsOfDateParamName, chargeCalculationTypeParamName, percentageParamName,
                    amountPercentageAppliedToParamName, currencyParamName, amountWaivedParamName, amountWrittenOffParamName,
                    amountOutstandingParamName, amountOrPercentageParamName, amountParamName, amountPaidParamName, chargeOptionsParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_ON_HOLD_RESPONSE_DATA_PARAMETERS = new HashSet<>(Arrays.asList(idParamName,
            amountParamName, onHoldTransactionTypeParamName, onHoldTransactionDateParamName, onHoldReversedParamName));
}
