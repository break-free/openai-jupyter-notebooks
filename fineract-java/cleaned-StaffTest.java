
package org.apache.fineract.integrationtests.client;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.apache.fineract.client.models.PostStaffRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
public class StaffTest extends IntegrationTest {
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
    public Long getStaffId() {
        return retrieveFirst().orElseGet(this::create);
    }
    Long create() {
        return ok(fineract().staff
                .create3(new PostStaffRequest().officeId(1L).firstname("StaffTest " + random()).lastname("Staffer " + random())
                        .externalId(random()).joiningDate(LocalDate.now(ZoneId.of("UTC"))).dateFormat(dateFormat()).locale("en_US")))
                                .getResourceId();
    }
    Optional<Long> retrieveFirst() {
        var staff = ok(fineract().staff.retrieveAll16(1L, true, false, "ACTIVE"));
        if (staff.size() > 0) {
            return Optional.of((long) staff.get(0).getId());
        }
        return Optional.empty();
    }
}
