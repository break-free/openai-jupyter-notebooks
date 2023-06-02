
package org.apache.fineract.integrationtests.client;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.apache.fineract.client.models.GetOfficesResponse;
import org.apache.fineract.client.models.PostOfficesRequest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
public class OfficeTest extends IntegrationTest {
    @Test
    @Order(1)
    void createOne() {
        assertThat(ok(fineract().offices.createOffice(new PostOfficesRequest().name("TestOffice_" + random()).parentId(1L)
                .openingDate(LocalDate.now(ZoneId.of("UTC"))).dateFormat(dateFormat()).locale("en_US"))).getOfficeId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    void retrieveOneExistingInclDateFormat() { 
        List<GetOfficesResponse> response = ok(fineract().offices.retrieveOffices(true, null, null));
        assertThat(response.size()).isAtLeast(1);
        assertThat(response.get(0).getOpeningDate()).isNotNull();
    }
}
