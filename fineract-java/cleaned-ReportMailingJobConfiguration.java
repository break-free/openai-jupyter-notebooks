
package org.apache.fineract.infrastructure.reportmailingjob.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_report_mailing_job_configuration", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name" }, name = "unique_name") })
public class ReportMailingJobConfiguration extends AbstractPersistableCustom {
    private static final long serialVersionUID = 3099279770861263184L;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "value", nullable = false)
    private String value;
    protected ReportMailingJobConfiguration() {}
    private ReportMailingJobConfiguration(final String name, final String value) {
        this.name = name;
        this.value = value;
    }
    public static ReportMailingJobConfiguration newInstance(final String name, final String value) {
        return new ReportMailingJobConfiguration(name, value);
    }
    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
}
