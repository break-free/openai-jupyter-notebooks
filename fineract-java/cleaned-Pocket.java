
package org.apache.fineract.portfolio.self.pockets.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@SuppressWarnings("serial")
@Entity
@Table(name = "m_pocket", uniqueConstraints = { @UniqueConstraint(columnNames = { "app_user_id" }, name = "unique_app_user") })
public class Pocket extends AbstractPersistableCustom {
    @Column(name = "app_user_id", length = 20, nullable = false)
    private Long appUserId;
    public Long getAppUserId() {
        return appUserId;
    }
    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }
    protected Pocket() {}
    private Pocket(final Long appUserId) {
        this.appUserId = appUserId;
    }
    public static Pocket instance(final Long appUserId) {
        return new Pocket(appUserId);
    }
}
