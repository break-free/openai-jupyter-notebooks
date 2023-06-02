
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import org.apache.fineract.client.models.GetMakerCheckerResponse;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.commands.MakercheckersHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
@SuppressWarnings({ "unused" })
public class MakercheckerTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private MakercheckersHelper makercheckersHelper;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.makercheckersHelper = new MakercheckersHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void testMakerchekerInboxList() {
        final ArrayList<GetMakerCheckerResponse> makerCheckerList = this.makercheckersHelper.getMakerCheckerList();
        assertNotNull(makerCheckerList);
    }
}
