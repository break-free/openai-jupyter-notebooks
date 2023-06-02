
package org.apache.fineract.notification.domain;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.useradministration.domain.AppUser;
@Entity
@Table(name = "notification_mapper")
public class NotificationMapper extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser userId;
    @Column(name = "is_read")
    private boolean isRead;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    public NotificationMapper() {}
    public NotificationMapper(Notification notification, AppUser userId, boolean isRead, LocalDateTime createdAt) {
        this.notification = notification;
        this.userId = userId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }
    public Notification getNotification() {
        return notification;
    }
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    public AppUser getUserId() {
        return userId;
    }
    public void setUserId(AppUser userId) {
        this.userId = userId;
    }
    public boolean isRead() {
        return isRead;
    }
    public void setRead(boolean read) {
        isRead = read;
    }
    @Override
    public String toString() {
        return "NotificationMapper [notification=" + this.notification + ", userId=" + this.userId + ", isRead=" + this.isRead
                + ", createdAt=" + this.createdAt + ", getId()=" + this.getId() + "]";
    }
}
