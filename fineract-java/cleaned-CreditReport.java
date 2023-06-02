
package org.apache.fineract.infrastructure.creditbureau.domain;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_creditreport")
public final class CreditReport extends AbstractPersistableCustom {
    @Column(name = "credit_bureau_id")
    private Long creditBureauId;
    @Column(name = "national_id")
    private String nationalId;
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "credit_reports")
    private byte[] creditReports;
    private CreditReport() {}
    public static CreditReport instance(final Long creditBureauId, final String nationalId, final byte[] creditReports) {
        return new CreditReport(creditBureauId, nationalId, creditReports);
    }
    private CreditReport(final Long creditBureauId, final String nationalId, final byte[] creditReports) {
        this.creditBureauId = creditBureauId;
        this.nationalId = nationalId;
        this.creditReports = creditReports;
    }
    public byte[] getCreditReport() {
        return this.creditReports;
    }
}
