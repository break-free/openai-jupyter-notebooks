
package org.apache.fineract.accounting.rule.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AccountingRuleRepository extends JpaRepository<AccountingRule, Long>, JpaSpecificationExecutor<AccountingRule> {
    @Query("select accountingRule from AccountingRule accountingRule where accountingRule.office is null or accountingRule.office.id =:officeId")
    AccountingRule getAccountingRuleByOfficeId(@Param("officeId") Long officeId);
}
