
package org.apache.fineract.infrastructure.jobs.service;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
public class SchedulerStopListener implements JobListener {
    private static final String name = "Singlr Trigger Global Listener";
    private JobRegisterService jobRegisterService;
    public SchedulerStopListener(JobRegisterService jobRegisterService) {
        this.jobRegisterService = jobRegisterService;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void jobToBeExecuted(@SuppressWarnings("unused") final JobExecutionContext context) {
    }
    @Override
    public void jobExecutionVetoed(@SuppressWarnings("unused") final JobExecutionContext context) {
    }
    @Override
    public void jobWasExecuted(final JobExecutionContext context, @SuppressWarnings("unused") final JobExecutionException jobException) {
        final String schedulerName = context.getTrigger().getJobDataMap().getString(SchedulerServiceConstants.SCHEDULER_NAME);
        if (schedulerName != null) {
            final Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SchedulerStopListener.this.jobRegisterService.stopScheduler(schedulerName);
                }
            });
            newThread.run();
        }
    }
}
