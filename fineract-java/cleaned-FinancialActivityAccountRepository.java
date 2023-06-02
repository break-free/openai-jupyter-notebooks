
package org.apache.fineract.accounting.financialactivityaccount.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface FinancialActivityAccountRepository
        extends JpaRepository<FinancialActivityAccount, Long>, JpaSpecificationExecutor<FinancialActivityAccount> {
    @Query("select faa from FinancialActivityAccount faa where faa.financialActivityType = :financialActivityType")
    FinancialActivityAccount findByFinancialActivityType(@Param("financialActivityType") int financialAccountType);
}
