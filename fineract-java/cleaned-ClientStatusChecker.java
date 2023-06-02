
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class ClientStatusChecker {
    private static final Logger LOG = LoggerFactory.getLogger(ClientStatusChecker.class);
    private ClientStatusChecker() {
    }
    public static void verifyClientIsActive(final HashMap<String, Object> clientStatusHashMap) {
        assertEquals(300, (int) clientStatusHashMap.get("id"));
    }
    public static void verifyClientClosed(final HashMap<String, Object> clientStatusHashMap) {
        LOG.info("\n-------------------------------------- VERIFYING CLIENT IS CLOSED ------------------------------------");
        assertEquals(600, (int) clientStatusHashMap.get("id"));
        LOG.info("Client Status: {} \n", clientStatusHashMap);
    }
    public static void verifyClientPending(final HashMap<String, Object> clientStatusHashMap) {
        LOG.info("\n-------------------------------------- VERIFYING CLIENT IS PENDING ------------------------------------");
        assertEquals(100, (int) clientStatusHashMap.get("id"));
        LOG.info("Client Status: {} \n", clientStatusHashMap);
    }
    public static void verifyClientRejected(final HashMap<String, Object> clientStatusHashMap) {
        LOG.info("\n-------------------------------------- VERIFYING CLIENT IS REJECTED ------------------------------------");
        assertEquals(700, (int) clientStatusHashMap.get("id"));
        LOG.info("Client Status: {} \n", clientStatusHashMap);
    }
    public static void verifyClientActiavted(final HashMap<String, Object> clientStatusHashMap) {
        LOG.info("\n-------------------------------------- VERIFYING CLIENT IS ACTIVATED ------------------------------------");
        assertEquals(300, (int) clientStatusHashMap.get("id"));
        LOG.info("Client Status: {} \n", clientStatusHashMap);
    }
    public static void verifyClientWithdrawn(final HashMap<String, Object> clientStatusHashMap) {
        LOG.info("\n-------------------------------------- VERIFYING CLIENT IS WITHDRAWN ------------------------------------");
        assertEquals(800, (int) clientStatusHashMap.get("id"));
        LOG.info("Client Status: {} \n", clientStatusHashMap);
    }
}
