
package org.apache.fineract.integrationtests.common.recurringdeposit;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@SuppressWarnings("rawtypes")
public final class RecurringDepositAccountStatusChecker {
    private RecurringDepositAccountStatusChecker() {
    }
    private static final Logger LOG = LoggerFactory.getLogger(RecurringDepositAccountStatusChecker.class);
    private static final String RECURRING_DEPOSIT_ACCOUNT_URL = "/fineract-provider/api/v1/recurringdepositaccounts";
    public static void verifyRecurringDepositIsApproved(final HashMap recurringDepositStatusHashMap) {
        LOG.info("-------------------- VERIFYING RECURRING DEPOSIT APPLICATION IS APPROVED --------------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "approved"), "Error in Approving Recurring deposit application");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositIsPending(final HashMap recurringDepositStatusHashMap) {
        LOG.info("-------------------- VERIFYING RECURRING DEPOSIT APPLICATION IS PENDING --------------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "submittedAndPendingApproval"),
                "RECURRING DEPOSIT ACCOUNT IS NOT IN PENDING STATE");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositIsActive(final HashMap recurringDepositStatusHashMap) {
        LOG.info("----------------- VERIFYING RECURRING DEPOSIT APPLICATION IS ACTIVE -----------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "active"), "ERROR IN ACTIVATING THE RECURRING DEPOSIT APPLICATION");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositIsRejected(final HashMap recurringDepositStatusHashMap) {
        LOG.info("-------------- VERIFYING RECURRING DEPOSIT APPLICATION IS REJECTED ----------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "rejected"), "ERROR IN REJECTING THE RECURRING DEPOSIT APPLICATION");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositIsWithdrawn(final HashMap recurringDepositStatusHashMap) {
        LOG.info("---------------- VERIFYING RECURRING DEPOSIT APPLICATION IS WITHDRAWN ----------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "withdrawnByApplicant"),
                "ERROR IN WITHDRAW  THE RECURRING DEPOSIT APPLICATION");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositAccountIsClosed(final HashMap recurringDepositStatusHashMap) {
        LOG.info("--------------------- VERIFYING RECURRING DEPOSIT APPLICATION IS CLOSED ---------------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "closed"), "ERROR IN CLOSING THE RECURRING DEPOSIT APPLICATION");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositAccountIsNotActive(final HashMap recurringDepositStatusHashMap) {
        LOG.info("------------------ VERIFYING RECURRING DEPOSIT APPLICATION IS INACTIVE --------------------");
        Assertions.assertFalse(getStatus(recurringDepositStatusHashMap, "active"));
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static HashMap getStatusOfRecurringDepositAccount(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final String recurringDepositAccountID) {
        final String GET_STATUS_OF_RECURRING_DEPOSIT_ACCOUNT_URL = RECURRING_DEPOSIT_ACCOUNT_URL + "/" + recurringDepositAccountID + "?"
                + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, GET_STATUS_OF_RECURRING_DEPOSIT_ACCOUNT_URL, "status");
    }
    public static void verifyRecurringDepositAccountIsPrematureClosed(HashMap recurringDepositStatusHashMap) {
        LOG.info("--------------------- VERIFYING RECURRING DEPOSIT APPLICATION IS CLOSED ---------------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "prematureClosed"),
                "ERROR IN PREMATURELY CLOSING THE RECURRING DEPOSIT ACCOUNT");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    public static void verifyRecurringDepositAccountIsMatured(HashMap recurringDepositStatusHashMap) {
        LOG.info("--------------------- VERIFYING RECURRING DEPOSIT APPLICATION IS MATURED ---------------------");
        assertTrue(getStatus(recurringDepositStatusHashMap, "matured"), "ERROR IN MATURITY JOB OF THE RECURRING DEPOSIT ACCOUNT");
        LOG.info("{}", recurringDepositStatusHashMap.toString());
    }
    private static boolean getStatus(final HashMap recurringDepositStatusMap, final String recurringDepositStatusString) {
        return (Boolean) recurringDepositStatusMap.get(recurringDepositStatusString);
    }
}
