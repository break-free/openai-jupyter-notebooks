
package org.apache.fineract.adhocquery.service;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.adhocquery.data.AdHocData;
import org.apache.fineract.adhocquery.domain.ReportRunFrequency;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.core.service.database.DatabaseSpecificSQLGenerator;
import org.apache.fineract.infrastructure.jobs.annotation.CronTarget;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service(value = "adHocScheduledJobRunnerService")
@RequiredArgsConstructor
@Slf4j
public class AdHocScheduledJobRunnerServiceImpl implements AdHocScheduledJobRunnerService {
    private final AdHocReadPlatformService adHocReadPlatformService;
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseSpecificSQLGenerator sqlGenerator;
    @Transactional
    @Override
    @CronTarget(jobName = JobName.GENERATE_ADHOCCLIENT_SCEHDULE)
    @SuppressWarnings("UnnecessaryDefaultInEnumSwitch")
    public void generateClientSchedule() {
        final Collection<AdHocData> adhocs = this.adHocReadPlatformService.retrieveAllActiveAdHocQuery();
        if (adhocs.size() > 0) {
            adhocs.forEach(adhoc -> {
                boolean run = true;
                LocalDate next = null;
                if (adhoc.getReportRunFrequency() != null) {
                    if (adhoc.getLastRun() != null) {
                        LocalDate start = adhoc.getLastRun().toLocalDate();
                        LocalDate end = ZonedDateTime.now(DateUtils.getDateTimeZoneOfTenant()).toLocalDate();
                        switch (ReportRunFrequency.fromId(adhoc.getReportRunFrequency())) {
                            case DAILY:
                                next = start.plusDays(1);
                                run = Math.toIntExact(ChronoUnit.DAYS.between(start, end)) >= 1;
                            break;
                            case WEEKLY:
                                next = start.plusDays(7);
                                run = Math.toIntExact(ChronoUnit.DAYS.between(start, end)) >= 7;
                            break;
                            case MONTHLY:
                                next = start.plusMonths(1);
                                run = Math.toIntExact(ChronoUnit.MONTHS.between(start, end)) >= 1;
                            break;
                            case YEARLY:
                                next = start.plusYears(1);
                                run = Math.toIntExact(ChronoUnit.YEARS.between(start, end)) >= 1;
                            break;
                            case CUSTOM:
                                next = start.plusDays((long) adhoc.getReportRunEvery());
                                run = Math.toIntExact(ChronoUnit.DAYS.between(start, end)) >= adhoc.getReportRunEvery();
                            break;
                            default:
                                throw new IllegalStateException();
                        }
                    }
                }
                if (run) {
                    final StringBuilder insertSqlBuilder = new StringBuilder(900);
                    insertSqlBuilder.append("INSERT INTO ").append(adhoc.getTableName() + "(").append(adhoc.getTableFields() + ") ")
                            .append(adhoc.getQuery());
                    if (insertSqlBuilder.length() > 0) {
                        final int result = this.jdbcTemplate.update(insertSqlBuilder.toString());
                        log.info("{}: Records affected by generateClientSchedule: {}", ThreadLocalContextUtil.getTenant().getName(),
                                result);
                        this.jdbcTemplate.update("UPDATE m_adhoc SET last_run=? WHERE id=?", DateUtils.getLocalDateTimeOfTenant(),
                                adhoc.getId());
                    }
                } else {
                    log.info("{}: Skipping execution of {}, scheduled for execution on {}",
                            new Object[] { ThreadLocalContextUtil.getTenant().getName(), adhoc.getName(), next });
                }
            });
        } else {
            log.info("{}: Nothing to update by generateClientSchedule", ThreadLocalContextUtil.getTenant().getName());
        }
    }
}
