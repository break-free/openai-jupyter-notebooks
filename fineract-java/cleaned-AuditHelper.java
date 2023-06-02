
package org.apache.fineract.integrationtests.common;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class AuditHelper {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private static final Logger LOG = LoggerFactory.getLogger(AuditHelper.class);
    private static final String AUDIT_BASE_URL = "/fineract-provider/api/v1/audits?" + Utils.TENANT_IDENTIFIER;
    private static final String AUDITSEARCH_BASE_URL = "/fineract-provider/api/v1/audits/searchtemplate?" + Utils.TENANT_IDENTIFIER;
    public AuditHelper(final RequestSpecification requestSpec, final ResponseSpecification responseSpec) {
        this.requestSpec = requestSpec;
        this.responseSpec = responseSpec;
    }
    public List getAuditDetails(final Integer resourceId, final String actionName, final String entityName) {
        final String AUDIT_URL = AUDIT_BASE_URL + "&entityName=" + entityName + "&resourceId=" + resourceId + "&actionName=" + actionName
                + "&orderBy=id&sortBy=DSC";
        List<HashMap<String, Object>> responseAudits = Utils.performServerGet(requestSpec, responseSpec, AUDIT_URL, "");
        return responseAudits;
    }
    public List getAuditDetails(final int limit) {
        final String AUDIT_URL = AUDIT_BASE_URL + "&paged=true&limit=" + Integer.toString(limit);
        LinkedHashMap responseAudits = Utils.performServerGet(requestSpec, responseSpec, AUDIT_URL, "");
        return (List) responseAudits.get("pageItems");
    }
    public LinkedHashMap getAuditSearchTemplate() {
        return Utils.performServerGet(requestSpec, responseSpec, AUDITSEARCH_BASE_URL, "$");
    }
    public void verifyOneAuditOnly(List<HashMap<String, Object>> auditsToCheck, Integer id, String actionName, String entityType) {
        LOG.info("------------------------------CHECK IF AUDIT CREATED------------------------------------\n");
        assertEquals(1, auditsToCheck.size(), "More than one audit created");
        HashMap<String, Object> auditToCheck = auditsToCheck.get(0);
        String actual = auditToCheck.get("actionName").toString() + " is done on " + auditToCheck.get("entityName").toString() + " with id "
                + auditToCheck.get("resourceId").toString();
        String expected = actionName + " is done on " + entityType + " with id " + id;
        assertEquals(expected, actual, "Error in creating audit!");
    }
    public void verifyMultipleAuditsOnserver(List<HashMap<String, Object>> auditsRecievedInitial,
            List<HashMap<String, Object>> auditsRecieved, Integer id, String actionName, String entityType) {
        LOG.info("------------------------------CHECK IF AUDIT CREATED------------------------------------\n");
        assertEquals(auditsRecievedInitial.size() + 1, auditsRecieved.size(), "Audit is not Created");
        Comparator<HashMap<String, Object>> compareById = (HashMap<String, Object> a, HashMap<String, Object> b) -> a.get("id").toString()
                .compareTo(b.get("id").toString());
        Collections.sort(auditsRecieved, compareById.reversed());
        HashMap<String, Object> auditToCheck = auditsRecieved.get(0);
        String actual = auditToCheck.get("actionName").toString() + " is done on " + auditToCheck.get("entityName").toString() + " with id "
                + auditToCheck.get("resourceId").toString();
        String expected = actionName + " is done on " + entityType + " with id " + id;
        assertEquals(expected, actual, "Error in creating audit!");
    }
    public void verifyLimitParameterfor(final int limit) {
        assertEquals(limit, getAuditDetails(limit).size(), "Incorrect number of audits recieved for limit: " + Integer.toString(limit));
    }
    public void verifyOrderBysupported(final String orderByValue) {
        final String AUDIT_URL = AUDIT_BASE_URL + "&paged=true&orderBy=" + orderByValue;
        Utils.performServerGet(requestSpec, responseSpec, AUDIT_URL, "");
    }
}
