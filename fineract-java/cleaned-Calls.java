
package org.apache.fineract.client.util;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
public final class Calls {
    private Calls() {}
    public static <T> T ok(Call<T> call) throws CallFailedRuntimeException {
        return okR(call).body();
    }
    public static <T> Response<T> okR(Call<T> call) throws CallFailedRuntimeException {
        Response<T> response = executeU(call);
        if (response.isSuccessful()) {
            return response;
        }
        throw new CallFailedRuntimeException(call, response);
    }
    public static <T> Response<T> executeU(Call<T> call) throws CallFailedRuntimeException {
        try {
            return call.execute();
        } catch (IOException e) {
            throw new CallFailedRuntimeException(call, e);
        }
    }
}
