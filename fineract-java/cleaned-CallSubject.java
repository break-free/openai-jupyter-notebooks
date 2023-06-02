
package org.apache.fineract.integrationtests.client;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import com.google.common.truth.Truth;
import javax.annotation.Nullable;
import org.apache.fineract.client.util.Calls;
import retrofit2.Call;
public class CallSubject extends Subject {
    public static CallSubject assertThat(@Nullable Call<?> actual) {
        return Truth.assertAbout(calls()).that(actual);
    }
    public static Factory<CallSubject, Call<?>> calls() {
        return CallSubject::new;
    }
    private final Call<?> actual;
    protected CallSubject(FailureMetadata metadata, @Nullable Call<?> actual) {
        super(metadata, actual);
        this.actual = actual;
    }
    public void hasHttpStatus(int expectedHttpStatus) {
        check("httpStatus").that(Calls.executeU(actual).code()).isEqualTo(expectedHttpStatus);
    }
}
