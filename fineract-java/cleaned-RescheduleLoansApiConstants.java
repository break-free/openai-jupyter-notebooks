
package org.apache.fineract.portfolio.loanaccount.rescheduleloan;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
public final class RescheduleLoansApiConstants {
    private RescheduleLoansApiConstants() {
    }
    public static final String ENTITY_NAME = "RESCHEDULELOAN";
    public static final String LOAN_RESCHEDULE_REASON = "LoanRescheduleReason";
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";
    public static final String loanIdParamName = "loanId";
    public static final String graceOnPrincipalParamName = "graceOnPrincipal";
    public static final String recurringMoratoriumOnPrincipalPeriodsParamName = "recurringMoratoriumOnPrincipalPeriods";
    public static final String graceOnInterestParamName = "graceOnInterest";
    public static final String extraTermsParamName = "extraTerms";
    public static final String rescheduleFromDateParamName = "rescheduleFromDate";
    public static final String recalculateInterestParamName = "recalculateInterest";
    public static final String newInterestRateParamName = "newInterestRate";
    public static final String rescheduleReasonIdParamName = "rescheduleReasonId";
    public static final String rescheduleReasonCommentParamName = "rescheduleReasonComment";
    public static final String submittedOnDateParamName = "submittedOnDate";
    public static final String adjustedDueDateParamName = "adjustedDueDate";
    public static final String resheduleForMultiDisbursementNotSupportedErrorCode = "loan.reschedule.tranche.multidisbursement.error.code";
    public static final String resheduleWithInterestRecalculationNotSupportedErrorCode = "loan.reschedule.interestrecalculation.error.code";
    public static final String allCommandParamName = "all";
    public static final String approveCommandParamName = "approve";
    public static final String pendingCommandParamName = "pending";
    public static final String rejectCommandParamName = "reject";
    public static final String endDateParamName = "endDate";
    public static final String emiParamName = "emi";
    public static final String rejectedOnDateParam = "rejectedOnDate";
    public static final String approvedOnDateParam = "approvedOnDate";
    public static final Set<String> APPROVE_REQUEST_DATA_PARAMETERS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(localeParamName, dateFormatParamName, approvedOnDateParam)));
    public static final Set<String> commandParams = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(allCommandParamName, approveCommandParamName, pendingCommandParamName, rejectCommandParamName)));
}
