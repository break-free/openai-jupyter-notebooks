
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.xbrl.XBRLIntegrationTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XBRLIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(XBRLIntegrationTest.class);
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private XBRLIntegrationTestHelper xbrlHelper;
    @BeforeEach
    public void setUp() throws Exception {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @Test
    public void shouldRetrieveTaxonomyList() {
        this.xbrlHelper = new XBRLIntegrationTestHelper(this.requestSpec, this.responseSpec);
        final ArrayList<HashMap> taxonomyList = this.xbrlHelper.getTaxonomyList();
        verifyTaxonomyList(taxonomyList);
    }
    private void verifyTaxonomyList(final ArrayList<HashMap> taxonomyList) {
        LOG.info("--------------------VERIFYING TAXONOMY LIST--------------------------");
        assertEquals("AdministrativeExpense", taxonomyList.get(0).get("name"), "Checking for the 1st taxonomy");
    }
}
