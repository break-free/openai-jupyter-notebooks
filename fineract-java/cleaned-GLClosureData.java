
package org.apache.fineract.accounting.closure.data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Data;
import org.apache.fineract.organisation.office.data.OfficeData;
@Data
public class GLClosureData {
    private final Long id;
    private final Long officeId;
    private final String officeName;
    private final LocalDate closingDate;
    private final boolean deleted;
    private final LocalDate createdDate;
    private final LocalDate lastUpdatedDate;
    private final Long createdByUserId;
    private final String createdByUsername;
    private final Long lastUpdatedByUserId;
    private final String lastUpdatedByUsername;
    private final String comments;
    private Collection<OfficeData> allowedOffices = new ArrayList<>();
}
