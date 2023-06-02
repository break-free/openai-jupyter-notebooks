
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.fineract.integrationtests.common.AuditHelper;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class AuditIntegrationTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private ClientHelper clientHelper;
    private AuditHelper auditHelper;
    private static final SecureRandom rand = new SecureRandom();
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.auditHelper = new AuditHelper(this.requestSpec, this.responseSpec);
        this.clientHelper = new ClientHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void testAuditSearchTemplate() {
        LinkedHashMap auditSearchTemplate = this.auditHelper.getAuditSearchTemplate();
        assertNotNull(auditSearchTemplate);
        assertEquals(4, auditSearchTemplate.size()); 
        assertTrue(((List) auditSearchTemplate.get("actionNames")).size() > 0);
    }
    @SuppressWarnings("unchecked")
    @Test
    public void auditShouldbeCreated() {
        List<HashMap<String, Object>> auditsRecieved;
        List<HashMap<String, Object>> auditsRecievedInitial;
        final Integer clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientId);
        auditsRecieved = auditHelper.getAuditDetails(clientId, "CREATE", "CLIENT");
        auditHelper.verifyOneAuditOnly(auditsRecieved, clientId, "CREATE", "CLIENT");
        for (int i = 0; i < 4; i++) {
            auditsRecievedInitial = auditHelper.getAuditDetails(clientId, "CLOSE", "CLIENT");
            this.clientHelper.closeClient(clientId);
            auditsRecieved = auditHelper.getAuditDetails(clientId, "CLOSE", "CLIENT");
            auditHelper.verifyMultipleAuditsOnserver(auditsRecievedInitial, auditsRecieved, clientId, "CLOSE", "CLIENT");
            auditsRecievedInitial = auditHelper.getAuditDetails(clientId, "REACTIVATE", "CLIENT");
            this.clientHelper.reactivateClient(clientId);
            auditsRecieved = auditHelper.getAuditDetails(clientId, "REACTIVATE", "CLIENT");
            auditHelper.verifyMultipleAuditsOnserver(auditsRecievedInitial, auditsRecieved, clientId, "REACTIVATE", "CLIENT");
        }
        OfficeHelper officeHelper = new OfficeHelper(requestSpec, responseSpec);
        int officeId = officeHelper.createOffice("22 June 2020");
        auditsRecieved = auditHelper.getAuditDetails(officeId, "CREATE", "OFFICE");
        auditHelper.verifyOneAuditOnly(auditsRecieved, officeId, "CREATE", "OFFICE");
    }
    @Test
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    public void checkAuditsWithLimitParam() {
        final Integer clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        for (int i = 0; i < 4; i++) {
            this.clientHelper.closeClient(clientId);
            this.clientHelper.reactivateClient(clientId);
        }
        for (int i = 0; i < 3; i++) {
            int limit = rand.nextInt(7) + 1;
            auditHelper.verifyLimitParameterfor(limit);
        }
    }
    @Test
    public void checkIfOrderBySupported() {
        final List<String> shouldBeSupportedFor = Arrays.asList("checkedOnDate", "officeName", "resourceId", "clientId", "processingResult",
                "clientName", "maker", "subresourceId", "checker", "savingsAccountNo", "loanAccountNo", "groupName", "entityName",
                "madeOnDate", "id", "loanId", "actionName");
        for (int i = 0; i < shouldBeSupportedFor.size(); i++) {
            auditHelper.verifyOrderBysupported(shouldBeSupportedFor.get(i));
        }
    }
}
