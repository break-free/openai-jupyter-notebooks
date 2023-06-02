
package org.apache.fineract.portfolio.loanaccount.data;
import java.math.BigDecimal;
import java.time.LocalDate;
public class LoanApprovalData {
    private final LocalDate approvalDate;
    private final BigDecimal approvalAmount;
    private final BigDecimal netDisbursalAmount;
    private LocalDate approvedOnDate;
    private String note;
    private String dateFormat;
    private String locale;
    private transient Integer rowIndex;
    public static LoanApprovalData importInstance(LocalDate approvedOnDate, Integer rowIndex, String locale, String dateFormat) {
        return new LoanApprovalData(approvedOnDate, rowIndex, locale, dateFormat);
    }
    private LoanApprovalData(LocalDate approvedOnDate, Integer rowIndex, String locale, String dateFormat) {
        this.approvedOnDate = approvedOnDate;
        this.rowIndex = rowIndex;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.note = "";
        this.approvalAmount = null;
        this.approvalDate = null;
        this.netDisbursalAmount = null;
    }
    public LoanApprovalData(final BigDecimal approvalAmount, final LocalDate approvalDate, final BigDecimal netDisbursalAmount) {
        this.approvalDate = approvalDate;
        this.approvalAmount = approvalAmount;
        this.netDisbursalAmount = netDisbursalAmount;
    }
    public LocalDate getApprovalDate() {
        return this.approvalDate;
    }
    public BigDecimal getApprovalAmount() {
        return this.approvalAmount;
    }
    public BigDecimal getNetDisbursalAmount() {
        return this.netDisbursalAmount;
    }
}
