
package org.apache.fineract.portfolio.loanaccount.rescheduleloan.domain;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.exception.LoanRescheduleRequestNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class LoanRescheduleRequestRepositoryWrapper {
    private final LoanRescheduleRequestRepository loanRescheduleRequestRepository;
    @Autowired
    public LoanRescheduleRequestRepositoryWrapper(final LoanRescheduleRequestRepository loanRescheduleRequestRepository) {
        this.loanRescheduleRequestRepository = loanRescheduleRequestRepository;
    }
    @Transactional(readOnly = true)
    public LoanRescheduleRequest findOneWithNotFoundDetection(final Long id) {
        return this.findOneWithNotFoundDetection(id, false);
    }
    @Transactional(readOnly = true)
    public LoanRescheduleRequest findOneWithNotFoundDetection(final Long id, boolean loadLazyCollections) {
        final LoanRescheduleRequest loanRescheduleRequest = this.loanRescheduleRequestRepository.findById(id)
                .orElseThrow(() -> new LoanRescheduleRequestNotFoundException(id));
        if (loadLazyCollections) {
            loanRescheduleRequest.getLoan().initializeLazyCollections();
        }
        return loanRescheduleRequest;
    }
}
