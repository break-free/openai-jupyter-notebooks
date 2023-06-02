
package org.apache.fineract.portfolio.account.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AccountAssociationsRepository
        extends JpaRepository<AccountAssociations, Long>, JpaSpecificationExecutor<AccountAssociations> {
    @Query("select aa from AccountAssociations aa where aa.loanAccount.id= :loanId and aa.associationType = :associationType")
    AccountAssociations findByLoanIdAndType(@Param("loanId") Long loanId, @Param("associationType") Integer accountAssociationType);
    @Query("select aa from AccountAssociations aa where aa.savingsAccount.id= :savingsId and aa.associationType = :associationType")
    AccountAssociations findBySavingsIdAndType(@Param("savingsId") Long savingsId,
            @Param("associationType") Integer accountAssociationType);
}
