
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.fail;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.fineract.integrationtests.common.HookHelper;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.http.conn.HttpHostConnectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HookIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(HookIntegrationTest.class);
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private HookHelper hookHelper;
    private OfficeHelper officeHelper;
    @BeforeEach
    public void setUp() throws Exception {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.hookHelper = new HookHelper(this.requestSpec, this.responseSpec);
        this.officeHelper = new OfficeHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void shouldSendOfficeCreationNotification() {
        final String uniqueId = UUID.randomUUID().toString();
        final String payloadURL = "http:
        final Integer hookId = this.hookHelper.createHook(payloadURL);
        Assertions.assertNotNull(hookId);
        final Integer createdOfficeID = this.officeHelper.createOffice("01 January 2012");
        Assertions.assertNotNull(createdOfficeID);
        try {
            for (int i = 0; i < 6; i++) {
                try {
                    final String json = RestAssured.get(payloadURL.replace("?", "")).asString();
                    final Integer notificationOfficeId = JsonPath.with(json).get("officeId");
                    Assertions.assertEquals(createdOfficeID, notificationOfficeId,
                            "Equality check for created officeId and hook received payload officeId");
                    LOG.info("Notification Office Id - {}", notificationOfficeId);
                    i = 6;
                } catch (Exception e) {
                    TimeUnit.SECONDS.sleep(3);
                    i++;
                }
            }
        } catch (final Exception e) {
            if (e instanceof HttpHostConnectException) {
                fail("Failed to connect to https:
            }
            throw new RuntimeException(e);
        } finally {
            this.hookHelper.deleteHook(hookId.longValue());
        }
    }
    @Test
    public void createUpdateAndDeleteHook() {
        final String payloadURL = "http:
        final String updateURL = "http:
        Long hookId = this.hookHelper.createHook(payloadURL).longValue();
        Assertions.assertNotNull(hookId);
        this.hookHelper.verifyHookCreatedOnServer(hookId);
        LOG.info("---------------------SUCCESSFULLY CREATED AND VERIFIED HOOK------------------------- {}", hookId);
        this.hookHelper.updateHook(updateURL, hookId);
        this.hookHelper.verifyUpdateHook(updateURL, hookId);
        LOG.info("---------------------SUCCESSFULLY UPDATED AND VERIFIED HOOK------------------------- {}", hookId);
        this.hookHelper.deleteHook(hookId);
        this.hookHelper.verifyDeleteHook(hookId);
        LOG.info("---------------------SUCCESSFULLY DELETED AND VERIFIED HOOK------------------------- {}", hookId);
    }
}
