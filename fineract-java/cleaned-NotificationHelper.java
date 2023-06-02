
package org.apache.fineract.integrationtests.common;
import static org.awaitility.Awaitility.await;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.client.models.GetNotificationsResponse;
import org.apache.fineract.client.util.JSON;
@Slf4j
public final class NotificationHelper {
    private static final String NOTIFICATION_API_URL = "/fineract-provider/api/v1/notifications?" + Utils.TENANT_IDENTIFIER;
    private static final Gson GSON = new JSON().getGson();
    private NotificationHelper() {}
    public static GetNotificationsResponse getNotifications(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        final String GET_NOTIFICATIONS_URL = NOTIFICATION_API_URL;
        log.info("-----------------------------GET NOTIFICATIONS-----------------------------------");
        String response = Utils.performServerGet(requestSpec, responseSpec, GET_NOTIFICATIONS_URL);
        return GSON.fromJson(response, GetNotificationsResponse.class);
    }
    public static boolean areNotificationsAvailable(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        return getNotifications(requestSpec, responseSpec).getPageItems().size() > 0;
    }
    public static void waitUntilNotificationsAreAvailable(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        await().atMost(Duration.ofSeconds(30)).pollInterval(Duration.ofMillis(250))
                .until(() -> NotificationHelper.areNotificationsAvailable(requestSpec, responseSpec));
    }
}
