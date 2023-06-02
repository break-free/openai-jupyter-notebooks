
package org.apache.fineract.portfolio.self.account.service;
import java.util.Collection;
import org.apache.fineract.portfolio.self.account.data.SelfAccountTemplateData;
import org.apache.fineract.portfolio.self.account.data.SelfBeneficiariesTPTData;
import org.apache.fineract.useradministration.domain.AppUser;
public interface SelfBeneficiariesTPTReadPlatformService {
    Collection<SelfBeneficiariesTPTData> retrieveAll();
    Collection<SelfAccountTemplateData> retrieveTPTSelfAccountTemplateData(AppUser user);
    Long getTransferLimit(Long id, Long accountId, Integer accountType);
}
