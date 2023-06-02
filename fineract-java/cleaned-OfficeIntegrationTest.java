
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.integrationtests.common.OfficeDomain;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class OfficeIntegrationTest {
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
    public void testOfficeModification() {
        OfficeHelper oh = new OfficeHelper(requestSpec, responseSpec);
        int officeId = oh.createOffice("01 July 2007");
        String name = Utils.randomNameGenerator("New_Office_", 4);
        String date = "02 July 2007";
        String[] dateArr = { "2007", "7", "2" };
        oh.updateOffice(officeId, name, date);
        OfficeDomain newOffice = oh.retrieveOfficeByID(officeId);
        Assertions.assertTrue(name.equals(newOffice.getName()));
        Assertions.assertArrayEquals(dateArr, newOffice.getOpeningDate());
    }
}
