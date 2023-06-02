
package org.apache.fineract.portfolio.search.data;
import java.util.Arrays;
import java.util.List;
public interface AdHocQuerySearchConstants {
    String AD_HOC_SEARCH_QUERY_RESOURCE_NAME = "adHocQuery";
    String localeParamName = "locale";
    String dateFormatParamName = "dateFormat";
    String entitiesParamName = "entities";
    String loanStatusParamName = "loanStatus";
    String loanProductsParamName = "loanProducts";
    String officesParamName = "offices";
    String loanDateOptionParamName = "loanDateOption";
    String loanFromDateParamName = "loanFromDate";
    String loanToDateParamName = "loanToDate";
    String includeOutStandingAmountPercentageParamName = "includeOutStandingAmountPercentage";
    String outStandingAmountPercentageConditionParamName = "outStandingAmountPercentageCondition";
    String minOutStandingAmountPercentageParamName = "minOutStandingAmountPercentage";
    String maxOutStandingAmountPercentageParamName = "maxOutStandingAmountPercentage";
    String outStandingAmountPercentageParamName = "outStandingAmountPercentage";
    String includeOutstandingAmountParamName = "includeOutstandingAmount";
    String outstandingAmountConditionParamName = "outstandingAmountCondition";
    String minOutstandingAmountParamName = "minOutstandingAmount";
    String maxOutstandingAmountParamName = "maxOutstandingAmount";
    String outstandingAmountParamName = "outstandingAmount";
    String approvalDateOption = "approvalDate";
    String createDateOption = "createdDate";
    String disbursalDateOption = "disbursalDate";
    String allLoanStatusOption = "all";
    String activeLoanStatusOption = "active";
    String overpaidLoanStatusOption = "overpaid";
    String arrearsLoanStatusOption = "arrears";
    String closedLoanStatusOption = "closed";
    String writeoffLoanStatusOption = "writeoff";
    List<Object> entityTypeOptions = List.copyOf(Arrays.asList("clients", "groups", "loans", "clientIdentifiers"));
}
