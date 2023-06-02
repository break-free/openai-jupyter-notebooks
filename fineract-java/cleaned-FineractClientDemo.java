
package org.apache.fineract.client.test;
import java.util.List;
import org.apache.fineract.client.models.RetrieveOneResponse;
import org.apache.fineract.client.util.Calls;
import org.apache.fineract.client.util.FineractClient;
public class FineractClientDemo {
    void demoClient() {
        FineractClient fineract = FineractClient.builder().baseURL("https:
                .basicAuth("mifos", "password").build();
        List<RetrieveOneResponse> staff = Calls.ok(fineract.staff.retrieveAll16(1L, true, false, "ACTIVE"));
        String name = staff.get(0).getDisplayName();
    }
}
