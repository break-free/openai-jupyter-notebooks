
package org.apache.fineract.infrastructure.jobs.service;
public interface SchedulerServiceConstants {
    String JOB_KEY_SEPERATOR = " _ ";
    String TRIGGER_TYPE_CRON = "cron";
    String TRIGGER_TYPE_APPLICATION = "application";
    String TRIGGER_TYPE_REFERENCE = "TRIGGER_TYPE_REFERENCE";
    String SCHEDULER_EXCEPTION = "SchedulerException";
    String JOB_EXECUTION_EXCEPTION = "JobExecutionException";
    String JOB_METHOD_INVOCATION_FAILED_EXCEPTION = "JobMethodInvocationFailedException";
    String STATUS_SUCCESS = "success";
    String STATUS_FAILED = "failed";
    String DEFAULT_LISTENER_NAME = "Global Listener";
    int STACK_TRACE_LEVEL = 7;
    String TENANT_IDENTIFIER = "tenantIdentifier";
    String SCHEDULER = "Scheduler";
    String SCHEDULER_GROUP = "group";
    int DEFAULT_THREAD_COUNT = 7;
    int GROUP_THREAD_COUNT = 1;
    String SCHEDULER_NAME = "schedulerName";
}
