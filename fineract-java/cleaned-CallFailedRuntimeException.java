
package org.apache.fineract.client.util;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
public class CallFailedRuntimeException extends RuntimeException {
    private final Call<?> call;
    private final Response<?> response;
    public <T> CallFailedRuntimeException(Call<T> call, Throwable t) {
        super("HTTP failed: " + call.request().toString(), t);
        this.call = call;
        this.response = null;
    }
    public <T> CallFailedRuntimeException(Call<T> call, Response<T> response) {
        super(message(call, response));
        this.call = call;
        this.response = response;
    }
    private static String message(Call<?> call, Response<?> response) {
        StringBuilder sb = new StringBuilder("HTTP failed: " + call.request() + "; " + response);
        if (response.message() != null && !response.message().isEmpty()) {
            sb.append("; message: " + response.message());
        }
        String errorBody;
        try {
            errorBody = response.errorBody().string();
            if (errorBody != null) {
                sb.append("; errorBody: " + errorBody);
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }
    @SuppressWarnings("unchecked")
    public <T> Call<T> getCall() {
        return (Call<T>) call;
    }
    @SuppressWarnings("unchecked")
    public <T> Response<T> getResponse() {
        return (Response<T>) response;
    }
}
