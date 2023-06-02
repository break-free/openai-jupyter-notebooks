
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.ExternalServicesConfigurationHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ExternalServicesConfigurationTest {
    private static final Logger LOG = LoggerFactory.getLogger(ExternalServicesConfigurationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private ExternalServicesConfigurationHelper externalServicesConfigurationHelper;
    private ResponseSpecification httpStatusForidden;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.httpStatusForidden = new ResponseSpecBuilder().expectStatusCode(403).build();
    }
    @Test
    public void testExternalServicesConfiguration() {
        this.externalServicesConfigurationHelper = new ExternalServicesConfigurationHelper(this.requestSpec, this.responseSpec);
        String configName = "s3_access_key";
        ArrayList<HashMap> externalServicesConfig = ExternalServicesConfigurationHelper
                .getExternalServicesConfigurationByServiceName(requestSpec, responseSpec, "S3");
        Assertions.assertNotNull(externalServicesConfig);
        for (Integer configIndex = 0; configIndex < externalServicesConfig.size(); configIndex++) {
            String name = (String) externalServicesConfig.get(configIndex).get("name");
            String value = null;
            if (name.equals(configName)) {
                value = (String) externalServicesConfig.get(configIndex).get("value");
                if (value == null) {
                    value = "testnull";
                }
                String newValue = "test";
                LOG.info("{} : {}", name, value);
                HashMap arrayListValue = ExternalServicesConfigurationHelper.updateValueForExternaServicesConfiguration(requestSpec,
                        responseSpec, "S3", name, newValue);
                Assertions.assertNotNull(arrayListValue.get("value"));
                Assertions.assertEquals(arrayListValue.get("value"), newValue);
                HashMap arrayListValue1 = ExternalServicesConfigurationHelper.updateValueForExternaServicesConfiguration(requestSpec,
                        responseSpec, "S3", name, value);
                Assertions.assertNotNull(arrayListValue1.get("value"));
                Assertions.assertEquals(arrayListValue1.get("value"), value);
            }
        }
        configName = "username";
        externalServicesConfig = ExternalServicesConfigurationHelper.getExternalServicesConfigurationByServiceName(requestSpec,
                responseSpec, "SMTP");
        Assertions.assertNotNull(externalServicesConfig);
        for (Integer configIndex = 0; configIndex < externalServicesConfig.size(); configIndex++) {
            String name = (String) externalServicesConfig.get(configIndex).get("name");
            String value = null;
            if (name.equals(configName)) {
                value = (String) externalServicesConfig.get(configIndex).get("value");
                if (value == null) {
                    value = "testnull";
                }
                String newValue = "test";
                LOG.info("{} : {}", name, value);
                HashMap arrayListValue = ExternalServicesConfigurationHelper.updateValueForExternaServicesConfiguration(requestSpec,
                        responseSpec, "SMTP", name, newValue);
                Assertions.assertNotNull(arrayListValue.get("value"));
                Assertions.assertEquals(arrayListValue.get("value"), newValue);
                HashMap arrayListValue1 = ExternalServicesConfigurationHelper.updateValueForExternaServicesConfiguration(requestSpec,
                        responseSpec, "SMTP", name, value);
                Assertions.assertNotNull(arrayListValue1.get("value"));
                Assertions.assertEquals(arrayListValue1.get("value"), value);
            }
        }
    }
}
