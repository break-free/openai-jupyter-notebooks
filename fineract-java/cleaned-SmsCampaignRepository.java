
package org.apache.fineract.infrastructure.campaigns.sms.domain;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface SmsCampaignRepository extends JpaRepository<SmsCampaign, Long>, JpaSpecificationExecutor<SmsCampaign> {
    List<SmsCampaign> findByCampaignType(Integer campaignType);
    Collection<SmsCampaign> findByCampaignTypeAndTriggerTypeAndStatus(Integer campaignType, Integer triggerType, Integer status);
    Collection<SmsCampaign> findByTriggerTypeAndStatus(Integer triggerType, Integer status);
    Collection<SmsCampaign> findByTriggerType(Integer triggerType);
    @Query("SELECT campaign FROM SmsCampaign campaign WHERE campaign.paramValue LIKE :reportPattern AND campaign.triggerType=:triggerType AND campaign.status=300")
    List<SmsCampaign> findActiveSmsCampaigns(@Param("reportPattern") String reportPattern, @Param("triggerType") Integer triggerType);
}
