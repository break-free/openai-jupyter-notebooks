
package org.apache.fineract.infrastructure.creditbureau.data;
import java.io.Serializable;
public final class CreditReportData implements Serializable {
    private final Long id;
    @SuppressWarnings("unused")
    private final Long creditBureauId;
    @SuppressWarnings("unused")
    private final String nationalId;
    public static CreditReportData instance(final Long id, final Long creditBureauId, final String nationalId) {
        return new CreditReportData(id, creditBureauId, nationalId);
    }
    private CreditReportData(final Long id, final Long creditBureauId, final String nationalId) {
        this.id = id;
        this.creditBureauId = creditBureauId;
        this.nationalId = nationalId;
    }
}
