
package org.apache.fineract.integrationtests.common.loans;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
@SuppressWarnings("rawtypes")
public final class LoanStatusChecker {
    private LoanStatusChecker() {
    }
    public static void verifyLoanIsApproved(final HashMap loanStatusHashMap) {
        assertFalse(getStatus(loanStatusHashMap, "pendingApproval"));
    }
    public static void verifyLoanIsWaitingForDisbursal(final HashMap loanStatusHashMap) {
        assertTrue(getStatus(loanStatusHashMap, "waitingForDisbursal"));
    }
    public static void verifyLoanIsPending(final HashMap loanStatusHashMap) {
        assertTrue(getStatus(loanStatusHashMap, "pendingApproval"));
    }
    public static void verifyLoanIsActive(final HashMap loanStatusHashMap) {
        assertTrue(getStatus(loanStatusHashMap, "active"));
    }
    public static void verifyLoanAccountIsClosed(final HashMap loanStatusHashMap) {
        assertTrue(getStatus(loanStatusHashMap, "closed"));
    }
    public static void verifyLoanAccountIsNotActive(final HashMap loanStatusHashMap) {
        assertFalse(getStatus(loanStatusHashMap, "active"));
    }
    public static void verifyLoanAccountIsOverPaid(final HashMap loanStatusHashMap) {
        assertTrue(getStatus(loanStatusHashMap, "overpaid"));
    }
    public static void verifyLoanAccountForeclosed(final HashMap loanSubStatusHashMap) {
        assertEquals("Foreclosed", getSubStatus(loanSubStatusHashMap, "value"));
    }
    public static void verifyLoanAccountRejected(final HashMap loanSubStatusHashMap) {
        assertEquals("Rejected", getSubStatus(loanSubStatusHashMap, "value"));
    }
    public static HashMap<String, Object> getStatusOfLoan(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer loanID) {
        final String url = "/fineract-provider/api/v1/loans/" + loanID + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "status");
    }
    public static HashMap<String, Object> getSubStatusOfLoan(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final Integer loanID) {
        final String url = "/fineract-provider/api/v1/loans/" + loanID + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerGet(requestSpec, responseSpec, url, "subStatus");
    }
    private static boolean getStatus(final HashMap loanStatusMap, final String nameOfLoanStatusString) {
        return (Boolean) loanStatusMap.get(nameOfLoanStatusString);
    }
    private static String getSubStatus(final HashMap loanStatusMap, final String nameOfLoanStatusString) {
        return (String) loanStatusMap.get(nameOfLoanStatusString);
    }
}
