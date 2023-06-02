
package org.apache.fineract.portfolio.shareaccounts.domain;
import org.apache.fineract.portfolio.accounts.exceptions.ShareAccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ShareAccountRepositoryWrapper {
    private final ShareAccountRepository shareAccountRepository;
    @Autowired
    public ShareAccountRepositoryWrapper(final ShareAccountRepository shareAccountRepository) {
        this.shareAccountRepository = shareAccountRepository;
    }
    public ShareAccount findOneWithNotFoundDetection(final Long accountId) {
        return this.shareAccountRepository.findById(accountId).orElseThrow(() -> new ShareAccountNotFoundException(accountId));
    }
    public void save(final ShareAccount shareAccount) {
        this.shareAccountRepository.save(shareAccount);
    }
    public void saveAndFlush(final ShareAccount shareAccount) {
        this.shareAccountRepository.saveAndFlush(shareAccount);
    }
}
