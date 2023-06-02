
package org.apache.fineract.integrationtests.client;
import com.google.common.truth.BigDecimalSubject;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.ComparableSubject;
import com.google.common.truth.DoubleSubject;
import com.google.common.truth.FloatSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.LongSubject;
import com.google.common.truth.OptionalSubject;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Subject;
import com.google.common.truth.Truth;
import com.google.common.truth.Truth8;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Optional;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.apache.fineract.client.util.Calls;
import org.apache.fineract.client.util.FineractClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import retrofit2.Call;
import retrofit2.Response;
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class IntegrationTest {
    private static final SecureRandom random = new SecureRandom();
    private FineractClient fineract;
    protected FineractClient fineract() {
        if (fineract == null) {
            String url = System.getProperty("fineract.it.url", "https:
            fineract = FineractClient.builder().insecure(true).baseURL(url).tenant("default").basicAuth("mifos", "password")
                    .logging(Level.NONE).build();
        }
        return fineract;
    }
    protected String dateFormat() {
        return FineractClient.DATE_FORMAT;
    }
    @SuppressFBWarnings(value = {
            "DMI_RANDOM_USED_ONLY_ONCE" }, justification = "False positive for random object created and used only once")
    protected String random() {
        return Long.toString(random.nextLong());
    }
    protected <T> T ok(Call<T> call) {
        return Calls.ok(call);
    }
    protected <T> Response<T> okR(Call<T> call) {
        return Calls.okR(call);
    }
    protected <T> CallSubject assertThat(Call<T> call) {
        return CallSubject.assertThat(call);
    }
    public static IterableSubject assertThat(Iterable<?> actual) {
        return Truth.assertThat(actual);
    }
    public static <T extends Comparable<?>> ComparableSubject<T> assertThat(T actual) {
        return Truth.assertThat(actual);
    }
    public static BigDecimalSubject assertThat(BigDecimal actual) {
        return Truth.assertThat(actual);
    }
    public static Subject assertThat(Object actual) {
        return Truth.assertThat(actual);
    }
    public static LongSubject assertThat(Long actual) {
        return Truth.assertThat(actual);
    }
    public static DoubleSubject assertThat(Double actual) {
        return Truth.assertThat(actual);
    }
    public static FloatSubject assertThat(Float actual) {
        return Truth.assertThat(actual);
    }
    public static IntegerSubject assertThat(Integer actual) {
        return Truth.assertThat(actual);
    }
    public static BooleanSubject assertThat(Boolean actual) {
        return Truth.assertThat(actual);
    }
    public static StringSubject assertThat(String actual) {
        return Truth.assertThat(actual);
    }
    public static OptionalSubject assertThat(Optional<?> actual) {
        return Truth8.assertThat(actual);
    }
}
