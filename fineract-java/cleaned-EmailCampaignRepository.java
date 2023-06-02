
package org.apache.fineract.infrastructure.campaigns.email.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EmailCampaignRepository extends JpaRepository<EmailCampaign, Long>, JpaSpecificationExecutor<EmailCampaign> {
    @Query("SELECT campaign FROM EmailCampaign campaign WHERE campaign.paramValue LIKE :reportPattern AND campaign.campaignType=:type AND campaign.status=300")
    List<EmailCampaign> findActiveEmailCampaigns(@Param("reportPattern") String reportPattern, @Param("type") Integer type);
}
