
package org.apache.fineract.infrastructure.jobs.service;
public interface JobRegisterService {
    void executeJob(Long jobId);
    void rescheduleJob(Long jobId);
    void pauseScheduler();
    void startScheduler();
    boolean isSchedulerRunning();
    void stopScheduler(String name);
    void stopAllSchedulers();
}
