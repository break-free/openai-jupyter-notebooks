
package org.apache.fineract.infrastructure.hooks.processor;
import com.google.gson.JsonObject;
import java.util.Map;
import org.apache.fineract.infrastructure.hooks.processor.data.SmsProviderData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface WebHookService {
    String ENTITY_HEADER = "X-Fineract-Entity";
    String ACTION_HEADER = "X-Fineract-Action";
    String TENANT_HEADER = "Fineract-Platform-TenantId";
    String ENDPOINT_HEADER = "X-Fineract-Endpoint";
    String API_KEY_HEADER = "X-Fineract-API-Key";
    @GET(".")
    Call<Void> sendEmptyRequest();
    @POST(".")
    Call<Void> sendJsonRequest(@Header(ENTITY_HEADER) String entityHeader, @Header(ACTION_HEADER) String actionHeader,
            @Header(TENANT_HEADER) String tenantHeader, @Header(ENDPOINT_HEADER) String endpointHeader, @Body JsonObject result);
    @FormUrlEncoded
    @POST(".")
    Call<Void> sendFormRequest(@Header(ENTITY_HEADER) String entityHeader, @Header(ACTION_HEADER) String actionHeader,
            @Header(TENANT_HEADER) String tenantHeader, @Header(ENDPOINT_HEADER) String endpointHeader,
            @FieldMap Map<String, String> params);
    @POST(".")
    Call<Void> sendSmsBridgeRequest(@Header(ENTITY_HEADER) String entityHeader, @Header(ACTION_HEADER) String actionHeader,
            @Header(TENANT_HEADER) String tenantHeader, @Header(API_KEY_HEADER) String apiKeyHeader, @Body JsonObject result);
    @POST("/configuration")
    Call<String> sendSmsBridgeConfigRequest(@Body SmsProviderData config);
}
