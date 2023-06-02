
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.GlobalConfigurationHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class GlobalConfigInterestChargedFromDateSameAsDisbursalDateTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private GlobalConfigurationHelper globalConfigurationHelper;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @AfterEach
    public void tearDown() {
        GlobalConfigurationHelper.resetAllDefaultGlobalConfigurations(this.requestSpec, this.responseSpec);
        GlobalConfigurationHelper.verifyAllDefaultGlobalConfigurations(this.requestSpec, this.responseSpec);
    }
    @SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
    @Test
    public void testInterestChargedFromDateSameAsDisbursalDate() {
        this.globalConfigurationHelper = new GlobalConfigurationHelper(this.requestSpec, this.responseSpec);
        final ArrayList<HashMap> globalConfig = GlobalConfigurationHelper.getAllGlobalConfigurations(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(globalConfig);
        String configName = "interest-charged-from-date-same-as-disbursal-date";
        boolean newBooleanValue = true;
        for (Integer configIndex = 0; configIndex < globalConfig.size(); configIndex++) {
            if (globalConfig.get(configIndex).get("name").equals(configName)) {
                String configId = globalConfig.get(configIndex).get("id").toString();
                Integer updateConfigId = GlobalConfigurationHelper.updateEnabledFlagForGlobalConfiguration(this.requestSpec,
                        this.responseSpec, configId.toString(), newBooleanValue);
                Assertions.assertNotNull(updateConfigId);
                break;
            }
        }
    }
}
