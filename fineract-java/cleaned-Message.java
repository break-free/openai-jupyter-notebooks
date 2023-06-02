
package org.apache.fineract.infrastructure.gcm.domain;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.fineract.infrastructure.gcm.GcmConstants;
public final class Message implements Serializable {
    private final String collapseKey;
    private final Boolean delayWhileIdle;
    private final Integer timeToLive;
    private final Map<String, String> data;
    private final Boolean dryRun;
    private final String restrictedPackageName;
    private final String priority;
    private final Boolean contentAvailable;
    private final Notification notification;
    public enum Priority {
        NORMAL, HIGH
    }
    public static final class Builder {
        private final Map<String, String> data;
        private String collapseKey;
        private Boolean delayWhileIdle;
        private Integer timeToLive;
        private Boolean dryRun;
        private String restrictedPackageName;
        private String priority;
        private Boolean contentAvailable;
        private Notification notification;
        public Builder() {
            this.data = new LinkedHashMap<>();
        }
        public Builder collapseKey(String value) {
            collapseKey = value;
            return this;
        }
        public Builder delayWhileIdle(boolean value) {
            delayWhileIdle = value;
            return this;
        }
        public Builder timeToLive(int value) {
            timeToLive = value;
            return this;
        }
        public Builder addData(String key, String value) {
            data.put(key, value);
            return this;
        }
        public Builder dryRun(boolean value) {
            dryRun = value;
            return this;
        }
        public Builder restrictedPackageName(String value) {
            restrictedPackageName = value;
            return this;
        }
        public Builder priority(Priority value) {
            switch (value) {
                case NORMAL:
                    priority = GcmConstants.MESSAGE_PRIORITY_NORMAL;
                break;
                case HIGH:
                    priority = GcmConstants.MESSAGE_PRIORITY_HIGH;
                break;
            }
            return this;
        }
        public Builder notification(Notification value) {
            notification = value;
            return this;
        }
        public Builder contentAvailable(Boolean value) {
            contentAvailable = value;
            return this;
        }
        public Message build() {
            return new Message(this);
        }
    }
    private Message(Builder builder) {
        collapseKey = builder.collapseKey;
        delayWhileIdle = builder.delayWhileIdle;
        data = Collections.unmodifiableMap(builder.data);
        timeToLive = builder.timeToLive;
        dryRun = builder.dryRun;
        restrictedPackageName = builder.restrictedPackageName;
        priority = builder.priority;
        contentAvailable = builder.contentAvailable;
        notification = builder.notification;
    }
    public String getCollapseKey() {
        return collapseKey;
    }
    public Boolean isDelayWhileIdle() {
        return delayWhileIdle;
    }
    public Integer getTimeToLive() {
        return timeToLive;
    }
    public Boolean isDryRun() {
        return dryRun;
    }
    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }
    public String getPriority() {
        return priority;
    }
    public Boolean getContentAvailable() {
        return contentAvailable;
    }
    public Map<String, String> getData() {
        return data;
    }
    public Notification getNotification() {
        return notification;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Message(");
        if (priority != null) {
            builder.append("priority=").append(priority).append(", ");
        }
        if (contentAvailable != null) {
            builder.append("contentAvailable=").append(contentAvailable).append(", ");
        }
        if (collapseKey != null) {
            builder.append("collapseKey=").append(collapseKey).append(", ");
        }
        if (timeToLive != null) {
            builder.append("timeToLive=").append(timeToLive).append(", ");
        }
        if (delayWhileIdle != null) {
            builder.append("delayWhileIdle=").append(delayWhileIdle).append(", ");
        }
        if (dryRun != null) {
            builder.append("dryRun=").append(dryRun).append(", ");
        }
        if (restrictedPackageName != null) {
            builder.append("restrictedPackageName=").append(restrictedPackageName).append(", ");
        }
        if (notification != null) {
            builder.append("notification: ").append(notification).append(", ");
        }
        if (!data.isEmpty()) {
            builder.append("data: {");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                builder.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append("}");
        }
        if (builder.charAt(builder.length() - 1) == ' ') {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append(")");
        return builder.toString();
    }
}
