
package org.apache.fineract.client.services;
import java.util.Map;
import okhttp3.ResponseBody;
import org.apache.fineract.client.models.RunReportsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
public interface RunReportsApi {
    @GET("runreports/{reportName}")
    Call<RunReportsResponse> runReportGetData(@retrofit2.http.Path("reportName") String reportName,
            @QueryMap Map<String, String> parameters, @retrofit2.http.Query("isSelfServiceUserReport") Boolean isSelfServiceUserReport);
    @GET("runreports/{reportName}")
    Call<ResponseBody> runReportGetFile(@retrofit2.http.Path("reportName") String reportName, @QueryMap Map<String, String> parameters,
            @retrofit2.http.Query("isSelfServiceUserReport") Boolean isSelfServiceUserReport);
}
