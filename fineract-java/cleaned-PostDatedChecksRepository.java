
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface PostDatedChecksRepository extends JpaRepository<PostDatedChecks, Long>, JpaSpecificationExecutor<PostDatedChecks> {
    @Query("select pdc from PostDatedChecks pdc where pdc.status = 0 and pdc.loanRepaymentScheduleInstallment = :loanRepaymentScheduleInstallment")
    PostDatedChecks getPendingPostDatedCheck(
            @Param("loanRepaymentScheduleInstallment") LoanRepaymentScheduleInstallment loanRepaymentScheduleInstallment);
}
