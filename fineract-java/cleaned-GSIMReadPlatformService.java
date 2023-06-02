
package org.apache.fineract.portfolio.savings.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.savings.data.GSIMContainer;
import org.apache.fineract.portfolio.savings.data.GroupSavingsIndividualMonitoringAccountData;
public interface GSIMReadPlatformService {
    Collection<GroupSavingsIndividualMonitoringAccountData> findGsimAccountByParentAccountNumber(String parentAccountIds);
    Collection<GroupSavingsIndividualMonitoringAccountData> findGSIMAccountsByGSIMId(Long glimId);
    Collection<GroupSavingsIndividualMonitoringAccountData> findGSIMAccountsByGroupId(String groupId);
    Collection<GSIMContainer> findGSIMAccountContainerByGroupId(Long groupId);
    Collection<GSIMContainer> findGsimAccountContainerbyGsimAccountNumber(String accountNumber);
    Collection<GroupSavingsIndividualMonitoringAccountData> findGsimAccountByGroupIdandAccountNo(String groupId, String accountNo);
    List<GSIMContainer> findGsimAccountContainerbyGsimAccountId(Long parentAccountId);
    GroupSavingsIndividualMonitoringAccountData findGSIMAccountByGSIMId(Long gsimId);
}
