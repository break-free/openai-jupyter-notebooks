
package org.apache.fineract.useradministration.domain;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.service.DateUtils;
@Entity
@Table(name = "m_appuser_previous_password")
public class AppUserPreviousPassword extends AbstractPersistableCustom {
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "removal_date")
    private LocalDate removalDate;
    @Column(name = "password", nullable = false)
    private String password;
    protected AppUserPreviousPassword() {
    }
    public AppUserPreviousPassword(final AppUser user) {
        this.userId = user.getId();
        this.password = user.getPassword().trim();
        this.removalDate = DateUtils.getLocalDateOfTenant();
    }
    public String getPassword() {
        return this.password;
    }
}
