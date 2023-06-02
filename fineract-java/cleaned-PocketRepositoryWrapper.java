
package org.apache.fineract.portfolio.self.pockets.domain;
import org.apache.fineract.portfolio.self.pockets.exception.PocketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PocketRepositoryWrapper {
    private final PocketRepository pocketRepository;
    @Autowired
    public PocketRepositoryWrapper(final PocketRepository pocketRepository) {
        this.pocketRepository = pocketRepository;
    }
    public void saveAndFlush(final Pocket pocket) {
        this.pocketRepository.saveAndFlush(pocket);
    }
    public Long findByAppUserId(final Long appUserId) {
        return this.pocketRepository.findByAppUserId(appUserId);
    }
    public Long findByAppUserIdWithNotFoundDetection(final Long appUserId) {
        final Long pocketId = this.pocketRepository.findByAppUserId(appUserId);
        if (pocketId == null) {
            throw new PocketNotFoundException();
        }
        return pocketId;
    }
}
