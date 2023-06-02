
package org.apache.fineract.client.util;
import com.google.common.truth.Truth;
import com.google.common.truth.Truth8;
import okhttp3.Headers;
import okhttp3.MediaType;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
public class PartsTest {
    @Test
    void validMediaType() {
        Truth.assertThat(Parts.mediaType("test.jpg")).isEqualTo(MediaType.get("image/jpeg"));
    }
    @Test
    void dotMediaType() {
        Truth.assertThat(Parts.mediaType("test.")).isNull();
    }
    @Test
    void emptyMediaType() {
        Truth.assertThat(Parts.mediaType("")).isNull();
    }
    @Test
    void nullMediaType() {
        Truth.assertThat(Parts.mediaType(null)).isNull();
    }
    @Test
    void fileName() {
        Truth8.assertThat(Parts.fileName(Response.success(null, Headers.of("Content-Disposition", "attachment; filename=\"doc.pdf\""))))
                .hasValue("doc.pdf");
    }
    @Test
    void fileNameWithoutContentDisposition() {
        Truth8.assertThat(Parts.fileName(Response.success(null))).isEmpty();
    }
}
