
package org.apache.fineract.infrastructure.gcm;
public final class GcmConstants {
    public static final String title = "Hello !";
    public static final String defaultIcon = "default";
    public static final String PARAM_TO = "to";
    public static final String TOPIC_PREFIX = "/topics/";
    public static final String PARAM_COLLAPSE_KEY = "collapse_key";
    public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";
    public static final String PARAM_DRY_RUN = "dry_run";
    public static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";
    public static final String PARAM_TIME_TO_LIVE = "time_to_live";
    public static final String PARAM_PRIORITY = "priority";
    public static final String PARAM_CONTENT_AVAILABLE = "content_available";
    public static final String MESSAGE_PRIORITY_NORMAL = "normal";
    public static final String MESSAGE_PRIORITY_HIGH = "high";
    public static final String ERROR_UNAVAILABLE = "Unavailable";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "InternalServerError";
    public static final String TOKEN_CANONICAL_REG_ID = "registration_id";
    public static final String JSON_REGISTRATION_IDS = "registration_ids";
    public static final String JSON_TO = "to";
    public static final String JSON_PAYLOAD = "data";
    public static final String JSON_NOTIFICATION = "notification";
    public static final String JSON_NOTIFICATION_TITLE = "title";
    public static final String JSON_NOTIFICATION_BODY = "body";
    public static final String JSON_NOTIFICATION_ICON = "icon";
    public static final String JSON_NOTIFICATION_SOUND = "sound";
    public static final String JSON_NOTIFICATION_BADGE = "badge";
    public static final String JSON_NOTIFICATION_TAG = "tag";
    public static final String JSON_NOTIFICATION_COLOR = "color";
    public static final String JSON_NOTIFICATION_CLICK_ACTION = "click_action";
    public static final String JSON_NOTIFICATION_BODY_LOC_KEY = "body_loc_key";
    public static final String JSON_NOTIFICATION_BODY_LOC_ARGS = "body_loc_args";
    public static final String JSON_NOTIFICATION_TITLE_LOC_KEY = "title_loc_key";
    public static final String JSON_NOTIFICATION_TITLE_LOC_ARGS = "title_loc_args";
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_FAILURE = "failure";
    public static final String JSON_CANONICAL_IDS = "canonical_ids";
    public static final String JSON_MULTICAST_ID = "multicast_id";
    public static final String JSON_RESULTS = "results";
    public static final String JSON_ERROR = "error";
    public static final String JSON_MESSAGE_ID = "message_id";
    private GcmConstants() {
        throw new UnsupportedOperationException();
    }
    public static final Integer TIME_TO_LIVE = 30;
}
