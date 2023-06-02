
package org.apache.fineract.portfolio.loanaccount.service;
import java.util.Collection;
import org.apache.fineract.portfolio.loanaccount.data.GLIMContainer;
import org.apache.fineract.portfolio.loanaccount.data.GlimRepaymentTemplate;
import org.apache.fineract.portfolio.loanaccount.data.GroupLoanIndividualMonitoringAccountData;
public interface GLIMAccountInfoReadPlatformService {
    Collection<GroupLoanIndividualMonitoringAccountData> findGlimAccountsByGroupId(String groupId);
    Collection<GroupLoanIndividualMonitoringAccountData> findGlimAccountByGroupId(String groupId);
    Collection<GLIMContainer> findGlimAccount(Long groupId);
    Collection<GroupLoanIndividualMonitoringAccountData> findGlimAccountByGroupIdandAccountNo(String groupId, String accountNo);
    Collection<GLIMContainer> findGlimAccountbyGroupAndAccount(Long groupId, String accountNo);
    Collection<GroupLoanIndividualMonitoringAccountData> findGlimAccountByParentAccountId(String parentAccountIds);
    Collection<GroupLoanIndividualMonitoringAccountData> findGlimAccountsByGLIMId(Long glimId);
    Collection<GlimRepaymentTemplate> findglimRepaymentTemplate(Long glimId);
}
