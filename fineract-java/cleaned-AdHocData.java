
package org.apache.fineract.adhocquery.data;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.adhocquery.domain.ReportRunFrequency;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
@Getter
@RequiredArgsConstructor
public class AdHocData {
    private final Long id;
    private final String name;
    private final String query;
    private final String tableName;
    private final String tableFields;
    private final String email;
    private final boolean isActive;
    private final ZonedDateTime createdOn;
    private final Long createdById;
    private final Long updatedById;
    private final ZonedDateTime updatedOn;
    private final String createdBy;
    private final List<EnumOptionData> reportRunFrequencies;
    private final Long reportRunFrequency;
    private final Long reportRunEvery;
    private final ZonedDateTime lastRun;
    public static AdHocData template() {
        List<EnumOptionData> reportRunFrequencies = Arrays.stream(ReportRunFrequency.values())
                .map(rrf -> new EnumOptionData(rrf.getValue(), rrf.getCode(), rrf.getCode())).collect(Collectors.toList());
        return new AdHocData(null, null, null, null, null, null, false, null, null, null, null, null, reportRunFrequencies, null, null,
                null);
    }
}
