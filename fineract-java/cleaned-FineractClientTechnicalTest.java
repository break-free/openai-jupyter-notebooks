
package org.apache.fineract.client.test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.fineract.client.util.FineractClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
public class FineractClientTechnicalTest {
    @Test
    @Disabled 
    void testInvalidOperations() {
        FineractClient.Builder builder = FineractClient.builder().baseURL("http:
        builder.getRetrofitBuilder().validateEagerly(true); 
        builder.build();
    }
    @Test
    void testFineractClientBuilder() {
        assertThrows(IllegalStateException.class, () -> {
            FineractClient.builder().build();
        });
    }
}
