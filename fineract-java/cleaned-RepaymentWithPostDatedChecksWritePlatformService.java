
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
public interface RepaymentWithPostDatedChecksWritePlatformService {
    CommandProcessingResult deletePostDatedChecks(JsonCommand command);
    CommandProcessingResult updatePostDatedChecks(JsonCommand command);
    CommandProcessingResult bouncePostDatedChecks(JsonCommand command);
}
