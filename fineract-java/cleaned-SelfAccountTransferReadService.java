
package org.apache.fineract.portfolio.self.account.service;
import java.util.Collection;
import org.apache.fineract.portfolio.self.account.data.SelfAccountTemplateData;
import org.apache.fineract.useradministration.domain.AppUser;
public interface SelfAccountTransferReadService {
    Collection<SelfAccountTemplateData> retrieveSelfAccountTemplateData(AppUser user);
}
