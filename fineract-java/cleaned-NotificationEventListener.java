
package org.apache.fineract.notification.eventandlistener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.notification.data.NotificationData;
import org.apache.fineract.notification.service.NotificationWritePlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.apache.fineract.useradministration.domain.AppUserRepository;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class NotificationEventListener {
    private final NotificationWritePlatformService notificationWritePlatformService;
    private final AppUserRepository appUserRepository;
    public void receive(NotificationData notificationData) {
        Long appUserId = notificationData.getActorId();
        Set<Long> userIds = notificationData.getUserIds();
        if (notificationData.getOfficeId() != null) {
            List<Long> tempUserIds = new ArrayList<>(userIds);
            for (Long userId : tempUserIds) {
                AppUser appUser = appUserRepository.findById(userId).orElseThrow();
                if (!Objects.equals(appUser.getOffice().getId(), notificationData.getOfficeId())) {
                    userIds.remove(userId);
                }
            }
        }
        if (userIds.contains(appUserId)) {
            userIds.remove(appUserId);
        }
        notificationWritePlatformService.notify(userIds, notificationData.getObjectType(), notificationData.getObjectId(),
                notificationData.getAction(), notificationData.getActorId(), notificationData.getContent(),
                notificationData.isSystemGenerated());
    }
}
