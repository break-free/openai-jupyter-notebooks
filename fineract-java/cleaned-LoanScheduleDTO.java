
package org.apache.fineract.portfolio.loanaccount.loanschedule.data;
import java.util.List;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.apache.fineract.portfolio.loanaccount.loanschedule.domain.LoanScheduleModel;
public final class LoanScheduleDTO {
    private final List<LoanRepaymentScheduleInstallment> installments;
    private final LoanScheduleModel loanScheduleModel;
    private LoanScheduleDTO(final List<LoanRepaymentScheduleInstallment> installments, final LoanScheduleModel loanScheduleModel) {
        this.installments = installments;
        this.loanScheduleModel = loanScheduleModel;
    }
    public static LoanScheduleDTO from(final List<LoanRepaymentScheduleInstallment> installments,
            final LoanScheduleModel loanScheduleModel) {
        return new LoanScheduleDTO(installments, loanScheduleModel);
    }
    public List<LoanRepaymentScheduleInstallment> getInstallments() {
        return this.installments;
    }
    public LoanScheduleModel getLoanScheduleModel() {
        return this.loanScheduleModel;
    }
}
