
package org.apache.fineract.infrastructure.businessdate.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
@Getter
@Entity
@Table(name = "m_business_date", uniqueConstraints = { @UniqueConstraint(name = "uq_business_date_type", columnNames = { "type" }) })
public class BusinessDate extends AbstractAuditableCustom {
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BusinessDateType type;
    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;
    @Version
    private Long version;
    protected BusinessDate() {
    }
    protected BusinessDate(@NotNull BusinessDateType type, @NotNull LocalDate date) {
        this.type = type;
        this.date = date;
    }
    public static BusinessDate instance(@NotNull BusinessDateType businessDateType, @NotNull LocalDate date) {
        return new BusinessDate(businessDateType, date);
    }
    public void updateDate(LocalDate date) {
        this.date = date;
    }
}
