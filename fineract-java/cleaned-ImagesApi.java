
package org.apache.fineract.client.services;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
public interface ImagesApi {
    @POST("{entityType}/{entityId}/images")
    @retrofit2.http.Multipart
    Call<Void> create(@retrofit2.http.Path("entityType") String entityType, @retrofit2.http.Path("entityId") Long entityId,
            @retrofit2.http.Part okhttp3.MultipartBody.Part file);
    @GET("{entityType}/{entityId}/images")
    Call<ResponseBody> get(@retrofit2.http.Path("entityType") String entityType, @retrofit2.http.Path("entityId") Long entityId,
            @retrofit2.http.Query("maxWidth") Integer maxWidth, @retrofit2.http.Query("maxHeight") Integer maxHeight,
            @retrofit2.http.Query("output") String output);
    @PUT("{entityType}/{entityId}/images")
    @retrofit2.http.Multipart
    Call<Void> update(@retrofit2.http.Path("entityType") String entityType, @retrofit2.http.Path("entityId") Long entityId,
            @retrofit2.http.Part okhttp3.MultipartBody.Part file);
    @DELETE("{entityType}/{entityId}/images")
    Call<Void> delete(@retrofit2.http.Path("entityType") String entityType, @retrofit2.http.Path("entityId") Long entityId);
}
