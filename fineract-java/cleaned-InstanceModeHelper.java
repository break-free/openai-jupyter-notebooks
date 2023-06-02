
package org.apache.fineract.integrationtests.support.instancemode;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.client.models.ChangeInstanceModeRequest;
import org.apache.fineract.client.util.JSON;
import org.apache.fineract.integrationtests.common.Utils;
@SuppressWarnings({ "HideUtilityClassConstructor" })
public class InstanceModeHelper {
    private static final Gson GSON = new JSON().getGson();
    public static void changeMode(RequestSpecification requestSpec, ResponseSpecification responseSpec, boolean readEnabled,
            boolean writeEnabled, boolean batchWorkerEnabled, boolean batchManagerEnabled) {
        ChangeInstanceModeRequest request = new ChangeInstanceModeRequest().readEnabled(readEnabled).writeEnabled(writeEnabled)
                .batchWorkerEnabled(batchWorkerEnabled).batchManagerEnabled(batchManagerEnabled);
        String requestStr = GSON.toJson(request);
        Utils.performServerPut(requestSpec, responseSpec, "/fineract-provider/api/v1/instance-mode?" + Utils.TENANT_IDENTIFIER, requestStr);
    }
}
