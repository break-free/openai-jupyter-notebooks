
package org.apache.fineract.infrastructure.jobs.service;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.apache.fineract.infrastructure.businessdate.service.BusinessDateReadPlatformService;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.security.service.TenantDetailsService;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class SchedulerTriggerListener implements TriggerListener {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerTriggerListener.class);
    private static final SecureRandom random = new SecureRandom();
    private final SchedularWritePlatformService schedularService;
    private final TenantDetailsService tenantDetailsService;
    private final BusinessDateReadPlatformService businessDateReadPlatformService;
    @Override
    public String getName() {
        return "Fineract Global Scheduler Trigger Listener";
    }
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        LOG.debug("triggerFired() trigger={}, context={}", trigger, context);
    }
    @Override
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    public boolean vetoJobExecution(final Trigger trigger, final JobExecutionContext context) {
        final String tenantIdentifier = trigger.getJobDataMap().getString(SchedulerServiceConstants.TENANT_IDENTIFIER);
        final FineractPlatformTenant tenant = this.tenantDetailsService.loadTenantById(tenantIdentifier);
        ThreadLocalContextUtil.setTenant(tenant);
        HashMap<BusinessDateType, LocalDate> businessDates = businessDateReadPlatformService.getBusinessDates();
        ThreadLocalContextUtil.setBusinessDates(businessDates);
        final JobKey key = trigger.getJobKey();
        final String jobKey = key.getName() + SchedulerServiceConstants.JOB_KEY_SEPERATOR + key.getGroup();
        String triggerType = SchedulerServiceConstants.TRIGGER_TYPE_CRON;
        if (context.getMergedJobDataMap().containsKey(SchedulerServiceConstants.TRIGGER_TYPE_REFERENCE)) {
            triggerType = context.getMergedJobDataMap().getString(SchedulerServiceConstants.TRIGGER_TYPE_REFERENCE);
        }
        Integer maxNumberOfRetries = ThreadLocalContextUtil.getTenant().getConnection().getMaxRetriesOnDeadlock();
        Integer maxIntervalBetweenRetries = ThreadLocalContextUtil.getTenant().getConnection().getMaxIntervalBetweenRetries();
        Integer numberOfRetries = 0;
        boolean vetoJob = false;
        while (numberOfRetries <= maxNumberOfRetries) {
            try {
                vetoJob = this.schedularService.processJobDetailForExecution(jobKey, triggerType);
                numberOfRetries = maxNumberOfRetries + 1;
            } catch (Exception exception) { 
                LOG.warn("vetoJobExecution() not able to acquire the lock to update job running status at retry {} (of {}) for JobKey: {}",
                        numberOfRetries, maxNumberOfRetries, jobKey, exception);
                try {
                    int randomNum = random.nextInt(maxIntervalBetweenRetries + 1);
                    Thread.sleep(1000 + (randomNum * 1000));
                    numberOfRetries = numberOfRetries + 1;
                } catch (InterruptedException e) {
                    LOG.error("vetoJobExecution() caught an InterruptedException", e);
                }
            }
        }
        if (vetoJob) {
            LOG.warn(
                    "vetoJobExecution() WILL veto the execution (returning vetoJob == true; the job's execute method will NOT be called); "
                            + "maxNumberOfRetries={}, tenant={}, jobKey={}, triggerType={}, trigger={}, context={}",
                    maxNumberOfRetries, tenantIdentifier, jobKey, triggerType, trigger, context);
        }
        return vetoJob;
    }
    @Override
    public void triggerMisfired(final Trigger trigger) {
        LOG.error("triggerMisfired() trigger={}", trigger);
    }
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {
        LOG.debug("triggerComplete() trigger={}, context={}, completedExecutionInstruction={}", trigger, context, triggerInstructionCode);
    }
}
