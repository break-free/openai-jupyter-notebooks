
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.client.models.GetOfficesResponse;
import org.apache.fineract.client.models.PostClientsRequest;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.SchedulerJobHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.support.instancemode.ConfigureInstanceMode;
import org.apache.fineract.integrationtests.support.instancemode.InstanceModeSupportExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(InstanceModeSupportExtension.class)
public class InstanceModeIntegrationTest {
    private ResponseSpecification responseSpec200;
    private ResponseSpecification responseSpec405;
    private RequestSpecification requestSpec;
    private SchedulerJobHelper schedulerJobHelper;
    private int jobId;
    @BeforeEach
    public void setup() throws InterruptedException {
        Utils.initializeRESTAssured();
        requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        requestSpec.header("Fineract-Platform-TenantId", "default");
        responseSpec200 = new ResponseSpecBuilder().expectStatusCode(200).build();
        responseSpec405 = new ResponseSpecBuilder().expectStatusCode(405).build();
        schedulerJobHelper = new SchedulerJobHelper(requestSpec);
        String jobName = "Apply Annual Fee For Savings";
        jobId = schedulerJobHelper.getSchedulerJobIdByName(jobName);
    }
    @ConfigureInstanceMode(readEnabled = true, writeEnabled = false, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testGetHeadOfficeWorks_WhenInstanceModeIsReadOnly() {
        GetOfficesResponse result = OfficeHelper.getHeadOffice(requestSpec, responseSpec200);
        assertNotNull(result);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = true, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testGetHeadOfficeWorks_WhenInstanceModeIsWriteOnly() {
        GetOfficesResponse result = OfficeHelper.getHeadOffice(requestSpec, responseSpec200);
        assertNotNull(result);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = false, batchWorkerEnabled = true, batchManagerEnabled = true)
    @Test
    public void testGetHeadOfficeDoesntWork_WhenInstanceModeIsBatchOnly() {
        OfficeHelper.getHeadOffice(requestSpec, responseSpec405);
    }
    @ConfigureInstanceMode(readEnabled = true, writeEnabled = false, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testCreateClientDoesntWork_WhenReadOnly() {
        PostClientsRequest request = ClientHelper.defaultClientCreationRequest();
        ClientHelper.createClient(requestSpec, responseSpec405, request);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = true, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testCreateClientWorks_WhenWriteOnly() {
        PostClientsRequest request = ClientHelper.defaultClientCreationRequest();
        Integer result = ClientHelper.createClient(requestSpec, responseSpec200, request);
        assertNotNull(result);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = false, batchWorkerEnabled = true, batchManagerEnabled = true)
    @Test
    public void testCreateClientDoesntWork_WhenBatchOnly() {
        PostClientsRequest request = ClientHelper.defaultClientCreationRequest();
        ClientHelper.createClient(requestSpec, responseSpec405, request);
    }
    @ConfigureInstanceMode(readEnabled = true, writeEnabled = false, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testRunSchedulerJobDoesntWork_WhenReadOnly() {
        schedulerJobHelper.runSchedulerJob(jobId, responseSpec405);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = true, batchWorkerEnabled = false, batchManagerEnabled = false)
    @Test
    public void testRunSchedulerJobDoesntWork_WhenWriteOnly() {
        schedulerJobHelper.runSchedulerJob(jobId, responseSpec405);
    }
    @ConfigureInstanceMode(readEnabled = false, writeEnabled = false, batchWorkerEnabled = true, batchManagerEnabled = true)
    @Test
    public void testRunSchedulerJobWorks_WhenBatchOnly() {
        schedulerJobHelper.runSchedulerJob(jobId);
    }
}
