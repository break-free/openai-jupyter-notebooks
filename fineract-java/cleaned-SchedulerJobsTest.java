
package org.apache.fineract.integrationtests;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.fineract.infrastructure.jobs.service.JobName;
import org.apache.fineract.integrationtests.common.GlobalConfigurationHelper;
import org.apache.fineract.integrationtests.common.SchedulerJobHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
@Order(1)
public class SchedulerJobsTest {
    private final Map<Integer, Boolean> originalJobStatus = new HashMap<>();
    private RequestSpecification requestSpec;
    private SchedulerJobHelper schedulerJobHelper;
    private Boolean originalSchedulerStatus;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        requestSpec.header("Fineract-Platform-TenantId", "default");
        schedulerJobHelper = new SchedulerJobHelper(requestSpec);
        originalSchedulerStatus = schedulerJobHelper.getSchedulerStatus();
        for (Integer jobId : schedulerJobHelper.getAllSchedulerJobIds()) {
            Map<String, Object> schedulerJob = schedulerJobHelper.getSchedulerJobById(jobId);
            Boolean active = (Boolean) schedulerJob.get("active");
            originalJobStatus.put(jobId, active);
        }
    }
    @AfterEach
    public void tearDown() {
        schedulerJobHelper.updateSchedulerStatus(originalSchedulerStatus);
        for (Integer jobId : schedulerJobHelper.getAllSchedulerJobIds()) {
            schedulerJobHelper.updateSchedulerJob(jobId, originalJobStatus.get(jobId));
        }
    }
    @Test 
    public void testDateFormat() {
        schedulerJobHelper.updateSchedulerStatus(true);
        int minJobId = schedulerJobHelper.getAllSchedulerJobIds().stream().mapToInt(number -> number).min().orElse(Integer.MAX_VALUE);
        schedulerJobHelper.updateSchedulerJob(minJobId, true);
        String nextRunTimeText = await().until(() -> (String) schedulerJobHelper.getSchedulerJobById(minJobId).get("nextRunTime"),
                Objects::nonNull);
        DateTimeFormatter.ISO_INSTANT.parse(nextRunTimeText);
    }
    @Test
    @Disabled 
    public void testFlippingSchedulerStatus() throws InterruptedException {
        Boolean schedulerStatus = schedulerJobHelper.getSchedulerStatus();
        if (schedulerStatus == true) {
            schedulerJobHelper.updateSchedulerStatus(false);
            schedulerStatus = schedulerJobHelper.getSchedulerStatus();
            assertEquals(false, schedulerStatus, "Verifying Scheduler Job Status");
        } else {
            schedulerJobHelper.updateSchedulerStatus(true);
            schedulerStatus = schedulerJobHelper.getSchedulerStatus();
            assertEquals(true, schedulerStatus, "Verifying Scheduler Job Status");
        }
    }
    @Test
    public void testNumberOfJobs() {
        List<Integer> jobIds = schedulerJobHelper.getAllSchedulerJobIds();
        assertEquals(JobName.values().length, jobIds.size(), "Number of jobs in database and code do not match: " + jobIds);
    }
    @Test
    public void testFlippingJobsActiveStatus() throws InterruptedException {
        schedulerJobHelper.updateSchedulerStatus(false);
        for (Integer jobId : schedulerJobHelper.getAllSchedulerJobIds()) {
            Map<String, Object> schedulerJob = schedulerJobHelper.getSchedulerJobById(jobId);
            Boolean active = (Boolean) schedulerJob.get("active");
            active = !active;
            Map<String, Object> changes = schedulerJobHelper.updateSchedulerJob(jobId, active);
            assertEquals(active, changes.get("active"), "Verifying Scheduler Job Updates");
            schedulerJob = schedulerJobHelper.getSchedulerJobById(jobId);
            assertEquals(active, schedulerJob.get("active"), "Verifying Get Scheduler Job");
        }
    }
    @Test
    public void testTriggeringManualExecutionOfAllSchedulerJobs() {
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        try {
            GlobalConfigurationHelper.updateIsBusinessDateEnabled(requestSpec, responseSpec, Boolean.TRUE);
            for (String jobName : schedulerJobHelper.getAllSchedulerJobNames()) {
                schedulerJobHelper.executeAndAwaitJob(jobName);
            }
        } finally {
            GlobalConfigurationHelper.updateIsBusinessDateEnabled(requestSpec, responseSpec, Boolean.FALSE);
        }
    }
}
