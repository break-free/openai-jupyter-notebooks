
package org.apache.fineract.accounting.glaccount.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface TrialBalanceRepository extends JpaRepository<TrialBalance, Long>, JpaSpecificationExecutor<TrialBalance> {
    @Query(value = "select * from m_trial_balance where office_id=:officeId and account_id=:accountId and closing_balance is null order by created_date, entry_date", nativeQuery = true)
    List<TrialBalance> findNewByOfficeAndAccount(@Param("officeId") Long officeId, @Param("accountId") Long accountId);
}
