
package org.apache.fineract.infrastructure.gcm.domain;
public class NotificationConfigurationData {
    private final Long id;
    private final String serverKey;
    private final String gcmEndPoint;
    private final String fcmEndPoint;
    public NotificationConfigurationData(Long id, String serverKey, final String gcmEndPoint, final String fcmEndPoint) {
        this.id = id;
        this.serverKey = serverKey;
        this.gcmEndPoint = gcmEndPoint;
        this.fcmEndPoint = fcmEndPoint;
    }
    public Long getId() {
        return id;
    }
    public String getServerKey() {
        return serverKey;
    }
    public String getGcmEndPoint() {
        return gcmEndPoint;
    }
    public String getFcmEndPoint() {
        return fcmEndPoint;
    }
}
