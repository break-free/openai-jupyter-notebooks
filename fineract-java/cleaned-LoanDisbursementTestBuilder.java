
package org.apache.fineract.integrationtests.common.loans;
public class LoanDisbursementTestBuilder {
    String dueDate = null;
    Float totalOriginalDueForPeriod = null;
    Float totalOutstandingForPeriod = null;
    Float interestOutstanding = null;
    Float principalOutstanding = null;
    Float principalLoanBalanceOutstanding = null;
    Float principalDue = null;
    Float principalOriginalDue = null;
    String fromDate = null;
    public LoanDisbursementTestBuilder(String dueDate, Float totalOriginalDueForPeriod, Float totalOutstandingForPeriod,
            Float interestOutstanding, Float principalOutstanding, Float principalLoanBalanceOutstanding, Float principalDue,
            Float principalOriginalDue, String fromDate) {
        this.dueDate = dueDate;
        this.totalOriginalDueForPeriod = totalOriginalDueForPeriod;
        this.totalOutstandingForPeriod = totalOutstandingForPeriod;
        this.interestOutstanding = interestOutstanding;
        this.principalOutstanding = principalOutstanding;
        this.principalLoanBalanceOutstanding = principalLoanBalanceOutstanding;
        this.principalDue = principalDue;
        this.principalOriginalDue = principalOriginalDue;
        this.fromDate = fromDate;
    }
    public String getDueDate() {
        return this.dueDate;
    }
    public Float getTotalOriginalDueForPeriod() {
        return this.totalOriginalDueForPeriod;
    }
    public Float getTotalOutstandingForPeriod() {
        return this.totalOutstandingForPeriod;
    }
    public Float getInterestOutstanding() {
        return this.interestOutstanding;
    }
    public Float getPrincipalOutstanding() {
        return this.principalOutstanding;
    }
    public Float getPrincipalLoanBalanceOutstanding() {
        return this.principalLoanBalanceOutstanding;
    }
    public Float getPrincipalDue() {
        return this.principalDue;
    }
    public Float getPrincipalOriginalDue() {
        return this.principalOriginalDue;
    }
    public String getFromDate() {
        return this.fromDate;
    }
}
