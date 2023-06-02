
package org.apache.fineract.infrastructure.jobs.domain;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "job_parameters")
public class JobParameter extends AbstractPersistableCustom {
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    @Column(name = "parameter_name", nullable = true)
    private String parameterName;
    @Column(name = "parameter_value", nullable = true)
    private String parameterValue;
    public JobParameter() {}
    public JobParameter(final Long jobId, final String parameterName, final String parameterValue) {
        this.jobId = jobId;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }
    public static JobParameter getInstance(final Long jobId, final String parameterName, final String parameterValue) {
        return new JobParameter(jobId, parameterName, parameterValue);
    }
    public Long getJobId() {
        return jobId;
    }
    public void setJobId(final Long jobId) {
        this.jobId = jobId;
    }
    public String getParameterName() {
        return parameterName;
    }
    public void setParameterName(final String parameterName) {
        this.parameterName = parameterName;
    }
    public String getParameterValue() {
        return parameterValue;
    }
    public void setParameterValue(final String parameterValue) {
        this.parameterValue = parameterValue;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JobParameter)) {
            return false;
        }
        JobParameter jobParameter = (JobParameter) obj;
        return Objects.equals(jobParameter.getJobId(), this.getJobId())
                && Objects.equals(jobParameter.getParameterName(), this.getParameterName())
                && Objects.equals(jobParameter.getParameterValue(), this.getParameterValue());
    }
    @Override
    public int hashCode() {
        return Objects.hash(jobId, parameterName, parameterValue);
    }
}
