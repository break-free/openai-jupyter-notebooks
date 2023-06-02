
package org.apache.fineract.integrationtests.common.commands;
import com.google.gson.Gson;
import com.linecorp.armeria.internal.shaded.guava.reflect.TypeToken;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.fineract.client.models.GetMakerCheckerResponse;
import org.apache.fineract.client.util.JSON;
import org.apache.fineract.integrationtests.common.Utils;
public class MakercheckersHelper {
    private static final Gson GSON = new JSON().getGson();
    private static final String MAKERCHECKER_URL = "/fineract-provider/api/v1/makercheckers?" + Utils.TENANT_IDENTIFIER;
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;
    public MakercheckersHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public ArrayList<GetMakerCheckerResponse> getMakerCheckerList() {
        final String response = Utils.performServerGet(this.requestSpec, this.responseSpec, MAKERCHECKER_URL);
        Type makerCheckerList = new TypeToken<ArrayList<GetMakerCheckerResponse>>() {}.getType();
        return GSON.fromJson(response, makerCheckerList);
    }
}
