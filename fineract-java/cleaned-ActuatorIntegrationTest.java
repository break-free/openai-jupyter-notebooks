
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Test;
public class ActuatorIntegrationTest {
    private static final String INFO_URL = "/fineract-provider/actuator/info";
    @Test
    public void testActuatorGitBuildInfo() {
        Response response = RestAssured.given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).when().get(INFO_URL)
                .then().contentType(ContentType.JSON).extract().response();
        Map<String, String> gitBuildInfo = response.jsonPath().getMap("git");
        assertTrue(gitBuildInfo.containsKey("branch"));
        assertTrue(gitBuildInfo.containsKey("remote"));
    }
}
