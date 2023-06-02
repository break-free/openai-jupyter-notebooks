
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.integrationtests.common.CreditBureauConfigurationHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class CreditBureauConfigurationTest {
    private static final Logger LOG = LoggerFactory.getLogger(CreditBureauConfigurationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private LoanTransactionHelper loanTransactionHelper;
    private static final String NO_ACCOUNTING = "1";
    static final int MYTHREADS = 30;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.loanTransactionHelper = new LoanTransactionHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void creditBureauConfigurationTest() {
        final Integer configurationId = CreditBureauConfigurationHelper.createCreditBureauConfiguration(this.requestSpec, this.responseSpec,
                Utils.randomNameGenerator("testConfigKey_", 5));
        Assertions.assertNotNull(configurationId);
        final String updateconfiguration = CreditBureauConfigurationHelper.updateCreditBureauConfiguration(this.requestSpec,
                this.responseSpec, configurationId);
        Assertions.assertEquals("updateConfigKeyValue", updateconfiguration);
    }
}
