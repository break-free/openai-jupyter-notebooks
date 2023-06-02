
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service;
import java.util.List;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.data.PostDatedChecksData;
public interface RepaymentWithPostDatedChecksReadPlatformService {
    List<PostDatedChecksData> getPostDatedChecks(Long id);
    PostDatedChecksData getPostDatedCheck(Long id);
    PostDatedChecksData getPostDatedCheckByInstallmentId(Integer id, Long loanId);
}
