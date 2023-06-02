
package org.apache.fineract.client.services;
import java.util.List;
import okhttp3.ResponseBody;
import org.apache.fineract.client.models.DeleteEntityTypeEntityIdDocumentsResponse;
import org.apache.fineract.client.models.GetEntityTypeEntityIdDocumentsResponse;
import org.apache.fineract.client.models.PostEntityTypeEntityIdDocumentsResponse;
import org.apache.fineract.client.models.PutEntityTypeEntityIdDocumentsResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
public interface DocumentsApiFixed {
    @retrofit2.http.Multipart
    @POST("{entityType}/{entityId}/documents")
    Call<PostEntityTypeEntityIdDocumentsResponse> createDocument(@retrofit2.http.Path("entityType") String entityType,
            @retrofit2.http.Path("entityId") Long entityId, @retrofit2.http.Part okhttp3.MultipartBody.Part file,
            @retrofit2.http.Part("name") String name, @retrofit2.http.Part("description") String description);
    @DELETE("{entityType}/{entityId}/documents/{documentId}")
    Call<DeleteEntityTypeEntityIdDocumentsResponse> deleteDocument(@retrofit2.http.Path("entityType") String entityType,
            @retrofit2.http.Path("entityId") Long entityId, @retrofit2.http.Path("documentId") Long documentId);
    @GET("{entityType}/{entityId}/documents/{documentId}/attachment")
    Call<ResponseBody> downloadFile(@retrofit2.http.Path("entityType") String entityType, @retrofit2.http.Path("entityId") Long entityId,
            @retrofit2.http.Path("documentId") Long documentId);
    @GET("{entityType}/{entityId}/documents/{documentId}")
    Call<GetEntityTypeEntityIdDocumentsResponse> getDocument(@retrofit2.http.Path("entityType") String entityType,
            @retrofit2.http.Path("entityId") Long entityId, @retrofit2.http.Path("documentId") Long documentId);
    @GET("{entityType}/{entityId}/documents")
    Call<List<GetEntityTypeEntityIdDocumentsResponse>> retrieveAllDocuments(@retrofit2.http.Path("entityType") String entityType,
            @retrofit2.http.Path("entityId") Long entityId);
    @retrofit2.http.Multipart
    @PUT("{entityType}/{entityId}/documents/{documentId}")
    Call<PutEntityTypeEntityIdDocumentsResponse> updateDocument(@retrofit2.http.Path("entityType") String entityType,
            @retrofit2.http.Path("entityId") Long entityId, @retrofit2.http.Path("documentId") Long documentId,
            @retrofit2.http.Part okhttp3.MultipartBody.Part file, @retrofit2.http.Part("name") String name,
            @retrofit2.http.Part("description") String description);
}
