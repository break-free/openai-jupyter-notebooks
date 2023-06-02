
package org.apache.fineract.infrastructure.dataqueries.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "stretchy_report_parameter")
public final class ReportParameterUsage extends AbstractPersistableCustom {
    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;
    @ManyToOne(optional = false)
    @JoinColumn(name = "parameter_id", nullable = false)
    private ReportParameter parameter;
    @Column(name = "report_parameter_name")
    private String reportParameterName;
    ReportParameterUsage() {
    }
    public ReportParameterUsage(final Report report, final ReportParameter parameter, final String reportParameterName) {
        this.report = report;
        this.parameter = parameter;
        this.reportParameterName = reportParameterName;
    }
    public boolean hasIdOf(final Long id) {
        return getId().equals(id);
    }
    public boolean hasParameterIdOf(final Long parameterId) {
        return this.parameter != null && this.parameter.hasIdOf(parameterId);
    }
    public void updateParameterName(final String parameterName) {
        this.reportParameterName = parameterName;
    }
    public String getReportParameterName() {
        return this.reportParameterName;
    }
}
