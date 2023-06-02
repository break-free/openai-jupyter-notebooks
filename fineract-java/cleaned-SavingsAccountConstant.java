
package org.apache.fineract.portfolio.savings.data;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.savings.SavingsApiConstants;
public class SavingsAccountConstant extends SavingsApiConstants {
    protected static final Set<String> SAVINGS_ACCOUNT_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, monthDayFormatParamName, staffIdParamName, isGSIM, isParentAccount, accountNoParamName,
            externalIdParamName, clientIdParamName, groupIdParamName, productIdParamName, fieldOfficerIdParamName, submittedOnDateParamName,
            nominalAnnualInterestRateParamName, interestCompoundingPeriodTypeParamName, interestPostingPeriodTypeParamName,
            interestCalculationTypeParamName, interestCalculationDaysInYearTypeParamName, minRequiredOpeningBalanceParamName,
            lockinPeriodFrequencyParamName, lockinPeriodFrequencyTypeParamName,
            withdrawalFeeForTransfersParamName, feeAmountParamName, feeOnMonthDayParamName, chargesParamName, allowOverdraftParamName,
            overdraftLimitParamName, minRequiredBalanceParamName, enforceMinRequiredBalanceParamName, lienAllowedParamName,
            maxAllowedLienLimitParamName, nominalAnnualInterestRateOverdraftParamName, minOverdraftForInterestCalculationParamName,
            withHoldTaxParamName, datatables, gsimApplicationId, gsimLastApplication));
    protected static final Set<String> SAVINGS_ACCOUNT_TRANSACTION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, transactionDateParamName, transactionAmountParamName,
                    paymentTypeIdParamName, transactionAccountNumberParamName, checkNumberParamName, routingCodeParamName,
                    receiptNumberParamName, bankNumberParamName, retailEntriesParamName, childAccountIdParamName, noteParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_TRANSACTION_RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(idParamName, accountNoParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_ACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, activatedOnDateParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_CLOSE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, closedOnDateParamName, noteParamName, paymentTypeIdParamName,
                    withdrawBalanceParamName, transactionAccountNumberParamName, checkNumberParamName, routingCodeParamName,
                    receiptNumberParamName, bankNumberParamName, postInterestValidationOnClosure));
    protected static final Set<String> SAVINGS_ACCOUNT_CHARGES_ADD_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(chargeIdParamName, amountParamName, dueAsOfDateParamName, dateFormatParamName, localeParamName,
                    feeOnMonthDayParamName, monthDayFormatParamName, feeIntervalParamName));
    protected static final Set<String> SAVINGS_ACCOUNT_CHARGES_PAY_CHARGE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(amountParamName, dueAsOfDateParamName, dateFormatParamName, localeParamName, noteParamName));
}
