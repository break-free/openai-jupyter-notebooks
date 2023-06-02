
package org.apache.fineract.portfolio.loanaccount.service;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class RecalculateInterestPoster implements Callable<Void> {
    private static final Logger LOG = LoggerFactory.getLogger(RecalculateInterestPoster.class);
    private static final SecureRandom random = new SecureRandom();
    private Collection<Long> loanIds;
    private LoanWritePlatformService loanWritePlatformService;
    public void setLoanIds(final Collection<Long> loanIds) {
        this.loanIds = loanIds;
    }
    public void setLoanWritePlatformService(final LoanWritePlatformService loanWritePlatformService) {
        this.loanWritePlatformService = loanWritePlatformService;
    }
    @Override
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    public Void call() throws JobExecutionException {
        Integer maxNumberOfRetries = ThreadLocalContextUtil.getTenant().getConnection().getMaxRetriesOnDeadlock();
        Integer maxIntervalBetweenRetries = ThreadLocalContextUtil.getTenant().getConnection().getMaxIntervalBetweenRetries();
        int i = 0;
        if (!loanIds.isEmpty()) {
            List<Throwable> errors = new ArrayList<>();
            for (Long loanId : loanIds) {
                LOG.info("Loan ID {}", loanId);
                Integer numberOfRetries = 0;
                while (numberOfRetries <= maxNumberOfRetries) {
                    try {
                        this.loanWritePlatformService.recalculateInterest(loanId);
                        numberOfRetries = maxNumberOfRetries + 1;
                    } catch (CannotAcquireLockException | ObjectOptimisticLockingFailureException exception) {
                        LOG.info("Recalulate interest job has been retried {} time(s)", numberOfRetries);
                        if (numberOfRetries >= maxNumberOfRetries) {
                            LOG.error("Recalulate interest job has been retried for the max allowed attempts of {} and will be rolled back",
                                    numberOfRetries);
                            errors.add(exception);
                            break;
                        }
                        try {
                            int randomNum = random.nextInt(maxIntervalBetweenRetries + 1);
                            Thread.sleep(1000 + (randomNum * 1000));
                            numberOfRetries = numberOfRetries + 1;
                        } catch (InterruptedException e) {
                            LOG.error("Interest recalculation for loans retry failed due to InterruptedException", e);
                            errors.add(e);
                            break;
                        }
                    } catch (Exception e) {
                        LOG.error("Interest recalculation for loans failed for account {}", loanId, e);
                        numberOfRetries = maxNumberOfRetries + 1;
                        errors.add(e);
                    }
                    i++;
                }
                LOG.info("Loans count {}", i);
            }
            if (!errors.isEmpty()) {
                throw new JobExecutionException(errors);
            }
        }
        return null;
    }
}
