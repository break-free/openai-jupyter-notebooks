
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.rates.RatesHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
@SuppressWarnings({ "rawtypes" })
public class RatesTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @Test
    public void testRatesForLoans() {
        ArrayList<HashMap> allRatesData = RatesHelper.getRates(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(allRatesData);
        final Integer loanRateId = RatesHelper.createRates(this.requestSpec, this.responseSpec, RatesHelper.getLoanRateJSON());
        Assertions.assertNotNull(loanRateId);
        HashMap changes = RatesHelper.updateRates(this.requestSpec, this.responseSpec, loanRateId, RatesHelper.getModifyRateJSON());
        HashMap rateDataAfterChanges = RatesHelper.getRateById(this.requestSpec, this.responseSpec, loanRateId);
        Assertions.assertEquals(rateDataAfterChanges.get("percentage"), changes.get("percentage"), "Verifying Rate after modification");
    }
}
