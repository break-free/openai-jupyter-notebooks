
package org.apache.fineract.integrationtests.client;
import java.util.Optional;
import org.apache.fineract.client.models.GetClientsResponse;
import org.apache.fineract.client.models.PostClientsRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
public class ClientTest extends IntegrationTest {
    @Test
    @Order(1)
    void createOne() {
        assertThat(create()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    void retrieveAnyExisting() {
        assertThat(retrieveFirst()).isPresent();
    }
    public Long getClientId() {
        return retrieveFirst().orElseGet(this::create);
    }
    Long create() {
        return ok(fineract().clients.create6(
                new PostClientsRequest().legalFormId(1).officeId(1).fullname("TestClient").dateFormat(dateFormat()).locale("en_US")))
                        .getClientId();
    }
    Optional<Long> retrieveFirst() {
        GetClientsResponse clients = ok(
                fineract().clients.retrieveAll21(null, null, null, null, null, null, null, null, 0, 1, null, null, false));
        if (clients.getTotalFilteredRecords() != null && clients.getTotalFilteredRecords() > 0) {
            return clients.getPageItems().stream().findFirst().map(item -> item.getId());
        }
        return Optional.empty();
    }
}
