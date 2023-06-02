
package org.apache.fineract.portfolio.shareaccounts.service;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountAssembler;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountDomainService;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccountDividendDetails;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccountDividendRepository;
import org.apache.fineract.portfolio.shareaccounts.domain.ShareAccountDividendStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ShareAccountSchedularServiceImpl implements ShareAccountSchedularService {
    private final ShareAccountDividendRepository shareAccountDividendRepository;
    private final SavingsAccountDomainService savingsAccountDomainService;
    private final SavingsAccountAssembler savingsAccountAssembler;
    @Autowired
    public ShareAccountSchedularServiceImpl(final ShareAccountDividendRepository shareAccountDividendRepository,
            final SavingsAccountDomainService savingsAccountDomainService, final SavingsAccountAssembler savingsAccountAssembler) {
        this.shareAccountDividendRepository = shareAccountDividendRepository;
        this.savingsAccountDomainService = savingsAccountDomainService;
        this.savingsAccountAssembler = savingsAccountAssembler;
    }
    @Override
    @Transactional
    public void postDividend(final Long dividendDetailId, final Long savingsId) {
        ShareAccountDividendDetails shareAccountDividendDetails = this.shareAccountDividendRepository.findById(dividendDetailId)
                .orElseThrow();
        final SavingsAccount savingsAccount = this.savingsAccountAssembler.assembleFrom(savingsId, false);
        SavingsAccountTransaction savingsAccountTransaction = this.savingsAccountDomainService.handleDividendPayout(savingsAccount,
                DateUtils.getBusinessLocalDate(), shareAccountDividendDetails.getAmount(), false);
        shareAccountDividendDetails.update(ShareAccountDividendStatusType.POSTED.getValue(), savingsAccountTransaction.getId());
        this.shareAccountDividendRepository.saveAndFlush(shareAccountDividendDetails);
    }
}
