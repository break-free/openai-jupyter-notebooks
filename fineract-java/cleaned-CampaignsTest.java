
package org.apache.fineract.integrationtests.common.organisation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.CommonConstants;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.MediaType;
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = { 9191 })
public class CampaignsTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private CampaignsHelper campaignsHelper;
    private static final String NON_TRIGGERED_REPORT_NAME = "Prospective Clients";
    private static final String TRIGGERED_REPORT_NAME = "Client Activated";
    private static final Integer DIRECT_TRIGGER_TYPE = 1;
    private static final Integer SCHEDULED_TRIGGER_TYPE = 2;
    private static final Integer TRIGGERED_TRIGGER_TYPE = 3;
    private static final String ACTIVATE_COMMAND = "activate";
    private static final String CLOSE_COMMAND = "close";
    private static final String REACTIVATE_COMMAND = "reactivate";
    public static final String DATE_FORMAT = "dd MMMM yyyy";
    private final ClientAndServer client;
    public CampaignsTest(ClientAndServer client) {
        this.client = client;
        this.client.when(request().withMethod("GET").withPath("/smsbridges"))
                .respond(response().withContentType(MediaType.APPLICATION_JSON).withBody("[\n" 
                        + "    {\n" 
                        + "        \"id\": 1,\n" 
                        + "        \"tenantId\": 1,\n" 
                        + "        \"phoneNo\": \"+1234567890\",\n" 
                        + "        \"providerName\": \"Dummy SMS Provider - Testing\",\n" 
                        + "        \"providerDescription\": \"Dummy, just for testing\"\n" 
                        + "     }\n" 
                        + "]") 
                );
    }
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.requestSpec.header("Fineract-Platform-TenantId", "default");
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.campaignsHelper = new CampaignsHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void testSupportedActionsForCampaignWithTriggerTypeAsDirect() {
        Integer campaignId = this.campaignsHelper.createCampaign(NON_TRIGGERED_REPORT_NAME, DIRECT_TRIGGER_TYPE);
        this.campaignsHelper.verifyCampaignCreatedOnServer(this.requestSpec, this.responseSpec, campaignId);
        Integer updatedCampaignId = this.campaignsHelper.updateCampaign(this.requestSpec, this.responseSpec, campaignId,
                NON_TRIGGERED_REPORT_NAME, DIRECT_TRIGGER_TYPE);
        assertEquals(campaignId, updatedCampaignId);
        Integer activatedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                ACTIVATE_COMMAND);
        assertEquals(activatedCampaignId, campaignId);
        Integer closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer reactivateCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                REACTIVATE_COMMAND);
        assertEquals(reactivateCampaignId, campaignId);
        closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId, CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer deletedCampaignId = this.campaignsHelper.deleteCampaign(this.requestSpec, this.responseSpec, campaignId);
        assertEquals(deletedCampaignId, campaignId);
    }
    @Test
    public void testSupportedActionsForCampaignWithTriggerTypeAsScheduled() {
        Integer campaignId = this.campaignsHelper.createCampaign(NON_TRIGGERED_REPORT_NAME, SCHEDULED_TRIGGER_TYPE);
        this.campaignsHelper.verifyCampaignCreatedOnServer(this.requestSpec, this.responseSpec, campaignId);
        Integer updatedCampaignId = this.campaignsHelper.updateCampaign(this.requestSpec, this.responseSpec, campaignId,
                NON_TRIGGERED_REPORT_NAME, SCHEDULED_TRIGGER_TYPE);
        assertEquals(campaignId, updatedCampaignId);
        Integer activatedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                ACTIVATE_COMMAND);
        assertEquals(activatedCampaignId, campaignId);
        Integer closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer reactivateCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                REACTIVATE_COMMAND);
        assertEquals(reactivateCampaignId, campaignId);
        closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId, CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer deletedCampaignId = this.campaignsHelper.deleteCampaign(this.requestSpec, this.responseSpec, campaignId);
        assertEquals(deletedCampaignId, campaignId);
    }
    @Test
    public void testSupportedActionsForCampaignWithTriggerTypeAsTriggered() {
        Integer campaignId = this.campaignsHelper.createCampaign(TRIGGERED_REPORT_NAME, TRIGGERED_TRIGGER_TYPE);
        this.campaignsHelper.verifyCampaignCreatedOnServer(this.requestSpec, this.responseSpec, campaignId);
        Integer updatedCampaignId = this.campaignsHelper.updateCampaign(this.requestSpec, this.responseSpec, campaignId,
                TRIGGERED_REPORT_NAME, TRIGGERED_TRIGGER_TYPE);
        assertEquals(campaignId, updatedCampaignId);
        Integer activatedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                ACTIVATE_COMMAND);
        assertEquals(activatedCampaignId, campaignId);
        Integer closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer reactivateCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                REACTIVATE_COMMAND);
        assertEquals(reactivateCampaignId, campaignId);
        closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId, CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer deletedCampaignId = this.campaignsHelper.deleteCampaign(this.requestSpec, this.responseSpec, campaignId);
        assertEquals(deletedCampaignId, campaignId);
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testSupportedActionsForCampaignWithError() {
        final ResponseSpecification responseSpecWithError = new ResponseSpecBuilder().expectStatusCode(400).build();
        CampaignsHelper campaignsHelperWithError = new CampaignsHelper(this.requestSpec, responseSpecWithError);
        Integer campaignId = this.campaignsHelper.createCampaign(NON_TRIGGERED_REPORT_NAME, DIRECT_TRIGGER_TYPE);
        this.campaignsHelper.verifyCampaignCreatedOnServer(this.requestSpec, this.responseSpec, campaignId);
        ArrayList<HashMap<String, Object>> campaignDateValidationData = (ArrayList<HashMap<String, Object>>) campaignsHelperWithError
                .performActionsOnCampaignWithFailure(campaignId, ACTIVATE_COMMAND,
                        Utils.getLocalDateOfTenant().plusDays(1).format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                        CommonConstants.RESPONSE_ERROR);
        assertEquals("error.msg.campaign.activationDate.in.the.future",
                campaignDateValidationData.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
        Integer activatedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                ACTIVATE_COMMAND);
        assertEquals(activatedCampaignId, campaignId);
        ArrayList<HashMap<String, Object>> campaignErrorData = (ArrayList<HashMap<String, Object>>) campaignsHelperWithError
                .performActionsOnCampaignWithFailure(activatedCampaignId, ACTIVATE_COMMAND,
                        Utils.getLocalDateOfTenant().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), CommonConstants.RESPONSE_ERROR);
        assertEquals("error.msg.campaign.already.active", campaignErrorData.get(0).get(CommonConstants.RESPONSE_ERROR_MESSAGE_CODE));
        Integer closedCampaignId = this.campaignsHelper.performActionsOnCampaign(this.requestSpec, this.responseSpec, campaignId,
                CLOSE_COMMAND);
        assertEquals(closedCampaignId, campaignId);
        Integer deletedCampaignId = this.campaignsHelper.deleteCampaign(this.requestSpec, this.responseSpec, campaignId);
        assertEquals(deletedCampaignId, campaignId);
    }
}
