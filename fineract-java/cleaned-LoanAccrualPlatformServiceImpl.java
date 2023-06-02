
package org.apache.fineract.portfolio.loanaccount.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.core.exception.MultiException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.portfolio.loanaccount.data.LoanScheduleAccrualData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoanAccrualPlatformServiceImpl implements LoanAccrualPlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(LoanAccrualPlatformServiceImpl.class);
    private final LoanReadPlatformService loanReadPlatformService;
    private final LoanAccrualWritePlatformService loanAccrualWritePlatformService;
    @Autowired
    public LoanAccrualPlatformServiceImpl(final LoanReadPlatformService loanReadPlatformService,
            final LoanAccrualWritePlatformService loanAccrualWritePlatformService) {
        this.loanReadPlatformService = loanReadPlatformService;
        this.loanAccrualWritePlatformService = loanAccrualWritePlatformService;
    }
    @Override
    @CronTarget(jobName = JobName.ADD_ACCRUAL_ENTRIES)
    public void addAccrualAccounting() throws JobExecutionException {
        Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas = this.loanReadPlatformService.retriveScheduleAccrualData();
        Map<Long, Collection<LoanScheduleAccrualData>> loanDataMap = new HashMap<>();
        for (final LoanScheduleAccrualData accrualData : loanScheduleAccrualDatas) {
            if (loanDataMap.containsKey(accrualData.getLoanId())) {
                loanDataMap.get(accrualData.getLoanId()).add(accrualData);
            } else {
                Collection<LoanScheduleAccrualData> accrualDatas = new ArrayList<>();
                accrualDatas.add(accrualData);
                loanDataMap.put(accrualData.getLoanId(), accrualDatas);
            }
        }
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<Long, Collection<LoanScheduleAccrualData>> mapEntry : loanDataMap.entrySet()) {
            try {
                this.loanAccrualWritePlatformService.addAccrualAccounting(mapEntry.getKey(), mapEntry.getValue());
            } catch (Exception e) {
                LOG.error("Failed to add accural transaction for loan {}", mapEntry.getKey(), e);
                errors.add(e);
            }
        }
        if (!errors.isEmpty()) {
            throw new JobExecutionException(errors);
        }
    }
    @Override
    @CronTarget(jobName = JobName.ADD_PERIODIC_ACCRUAL_ENTRIES)
    public void addPeriodicAccruals() throws JobExecutionException {
        try {
            addPeriodicAccruals(DateUtils.getBusinessLocalDate());
        } catch (MultiException e) {
            throw new JobExecutionException(e);
        }
    }
    @Override
    public void addPeriodicAccruals(final LocalDate tilldate) throws JobExecutionException {
        Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas = this.loanReadPlatformService.retrivePeriodicAccrualData(tilldate);
        addPeriodicAccruals(tilldate, loanScheduleAccrualDatas);
    }
    @Override
    public void addPeriodicAccruals(final LocalDate tilldate, Collection<LoanScheduleAccrualData> loanScheduleAccrualDatas)
            throws JobExecutionException {
        Map<Long, Collection<LoanScheduleAccrualData>> loanDataMap = new HashMap<>();
        for (final LoanScheduleAccrualData accrualData : loanScheduleAccrualDatas) {
            if (loanDataMap.containsKey(accrualData.getLoanId())) {
                loanDataMap.get(accrualData.getLoanId()).add(accrualData);
            } else {
                Collection<LoanScheduleAccrualData> accrualDatas = new ArrayList<>();
                accrualDatas.add(accrualData);
                loanDataMap.put(accrualData.getLoanId(), accrualDatas);
            }
        }
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<Long, Collection<LoanScheduleAccrualData>> mapEntry : loanDataMap.entrySet()) {
            try {
                this.loanAccrualWritePlatformService.addPeriodicAccruals(tilldate, mapEntry.getKey(), mapEntry.getValue());
            } catch (Exception e) {
                LOG.error("Failed to add accural transaction for loan {}", mapEntry.getKey(), e);
                errors.add(e);
            }
        }
        if (!errors.isEmpty()) {
            throw new JobExecutionException(errors);
        }
    }
    @Override
    @CronTarget(jobName = JobName.ADD_PERIODIC_ACCRUAL_ENTRIES_FOR_LOANS_WITH_INCOME_POSTED_AS_TRANSACTIONS)
    public void addPeriodicAccrualsForLoansWithIncomePostedAsTransactions() throws JobExecutionException {
        Collection<Long> loanIds = this.loanReadPlatformService.retrieveLoanIdsWithPendingIncomePostingTransactions();
        if (loanIds != null && loanIds.size() > 0) {
            List<Throwable> errors = new ArrayList<>();
            for (Long loanId : loanIds) {
                try {
                    this.loanAccrualWritePlatformService.addIncomeAndAccrualTransactions(loanId);
                } catch (Exception e) {
                    LOG.error("Failed to add income and accrual transaction for loan {}", loanId, e);
                    errors.add(e);
                }
            }
            if (!errors.isEmpty()) {
                throw new JobExecutionException(errors);
            }
        }
    }
}
