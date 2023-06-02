
package org.apache.fineract.integrationtests.common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.HttpMethod;
import org.apache.fineract.batch.command.internal.CreateTransactionLoanCommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class BatchHelper {
    private static final Logger LOG = LoggerFactory.getLogger(BatchHelper.class);
    private static final String BATCH_API_URL = "/fineract-provider/api/v1/batches?" + Utils.TENANT_IDENTIFIER;
    private static final String BATCH_API_URL_EXT = BATCH_API_URL + "&enclosingTransaction=true";
    private static final SecureRandom secureRandom = new SecureRandom();
    private BatchHelper() {
    }
    public static String toJsonString(final List<BatchRequest> batchRequests) {
        return new Gson().toJson(batchRequests);
    }
    public static Map generateMapFromJsonString(final String jsonString) {
        return new Gson().fromJson(jsonString, Map.class);
    }
    private static List<BatchResponse> fromJsonString(final String json) {
        return new Gson().fromJson(json, new TypeToken<List<BatchResponse>>() {}.getType());
    }
    public static List<BatchResponse> postBatchRequestsWithoutEnclosingTransaction(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final String jsonifiedBatchRequests) {
        final String response = Utils.performServerPost(requestSpec, responseSpec, BATCH_API_URL, jsonifiedBatchRequests, null);
        return BatchHelper.fromJsonString(response);
    }
    public static List<BatchResponse> postBatchRequestsWithEnclosingTransaction(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final String jsonifiedBatchRequests) {
        final String response = Utils.performServerPost(requestSpec, responseSpec, BATCH_API_URL_EXT, jsonifiedBatchRequests, null);
        return BatchHelper.fromJsonString(response);
    }
    public static List<BatchResponse> postWithSingleRequest(final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec, final BatchRequest br) {
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(requestSpec, responseSpec,
                jsonifiedRequest);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.size() > 0);
        return response;
    }
    public static BatchRequest createClientRequest(final Long requestId, final String externalId) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("clients");
        br.setMethod("POST");
        final String extId;
        if (externalId.equals("")) {
            extId = "ext" + String.valueOf((10000 * secureRandom.nextDouble())) + String.valueOf((10000 * secureRandom.nextDouble()));
        } else {
            extId = externalId;
        }
        final String body = "{ \"officeId\": 1, \"legalFormId\":1, \"firstname\": \"Petra\", \"lastname\": \"Yton\"," + "\"externalId\": "
                + extId + ",  \"dateFormat\": \"dd MMMM yyyy\", \"locale\": \"en\","
                + "\"active\": false, \"submittedOnDate\": \"04 March 2009\"}";
        br.setBody(body);
        return br;
    }
    public static BatchRequest createActiveClientRequest(final Long requestId, final String externalId) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("clients");
        br.setMethod("POST");
        final String extId;
        if (externalId.equals("")) {
            extId = "ext" + String.valueOf((10000 * secureRandom.nextDouble())) + String.valueOf((10000 * secureRandom.nextDouble()));
        } else {
            extId = externalId;
        }
        final String body = "{ \"officeId\": 1, \"legalFormId\":1, \"firstname\": \"Petra\", \"lastname\": \"Yton\"," + "\"externalId\": \""
                + externalId + "\",  \"dateFormat\": \"dd MMMM yyyy\", \"locale\": \"en\","
                + "\"active\": true, \"activationDate\": \"04 March 2010\", \"submittedOnDate\": \"04 March 2010\"}";
        br.setBody(body);
        return br;
    }
    public static BatchRequest updateClientRequest(final Long requestId, final Long reference) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("clients/$.clientId");
        br.setMethod("PUT");
        br.setReference(reference);
        br.setBody("{\"firstname\": \"TestFirstName\", \"lastname\": \"TestLastName\"}");
        return br;
    }
    public static BatchRequest applyLoanRequest(final Long requestId, final Long reference, final Integer productId,
            final Integer clientCollateralId) {
        return applyLoanRequest(requestId, reference, productId, clientCollateralId, LocalDate.now(ZoneId.systemDefault()).minusDays(10),
                "10,000.00");
    }
    public static BatchRequest applyLoanRequest(final Long requestId, final Long reference, final Integer productId,
            final Integer clientCollateralId, final LocalDate date, final String loanAmount) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans");
        br.setMethod("POST");
        br.setReference(reference);
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        String body = "{\"dateFormat\": \"dd MMMM yyyy\", \"locale\": \"en_GB\", \"clientId\": \"$.clientId\"," + "\"productId\": "
                + productId + ", \"principal\": \"" + loanAmount + "\", \"loanTermFrequency\": 10,"
                + "\"loanTermFrequencyType\": 2, \"loanType\": \"individual\", \"numberOfRepayments\": 10,"
                + "\"repaymentEvery\": 1, \"repaymentFrequencyType\": 2, \"interestRatePerPeriod\": 10,"
                + "\"amortizationType\": 1, \"interestType\": 0, \"interestCalculationPeriodType\": 1,"
                + "\"transactionProcessingStrategyId\": 1, \"expectedDisbursementDate\": \"" + dateString + "\",";
        if (clientCollateralId != null) {
            body = body + "\"collateral\": [{\"clientCollateralId\": \"" + clientCollateralId + "\", \"quantity\": \"1\"}],";
        }
        body = body + "\"submittedOnDate\": \"" + dateString + "\"}";
        br.setBody(body);
        return br;
    }
    public static BatchRequest applyLoanRequestWithClientId(final Long requestId, final Integer clientId, final Integer productId) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans");
        br.setMethod("POST");
        String body = String.format("{\"dateFormat\": \"dd MMMM yyyy\", \"locale\": \"en_GB\", \"clientId\": %s, "
                + "\"productId\": %s, \"principal\": \"10,000.00\", \"loanTermFrequency\": 10,"
                + "\"loanTermFrequencyType\": 2, \"loanType\": \"individual\", \"numberOfRepayments\": 10,"
                + "\"repaymentEvery\": 1, \"repaymentFrequencyType\": 2, \"interestRatePerPeriod\": 10,"
                + "\"amortizationType\": 1, \"interestType\": 0, \"interestCalculationPeriodType\": 1,"
                + "\"transactionProcessingStrategyId\": 1, \"expectedDisbursementDate\": \"10 Jun 2013\","
                + "\"submittedOnDate\": \"10 Jun 2013\"}", clientId, productId);
        br.setBody(body);
        return br;
    }
    public static BatchRequest applySavingsRequest(final Long requestId, final Long reference, final Integer productId) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("savingsaccounts");
        br.setMethod("POST");
        br.setReference(reference);
        final String body = "{\"clientId\": \"$.clientId\", \"productId\": " + productId + ","
                + "\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", \"submittedOnDate\": \"01 March 2011\"}";
        br.setBody(body);
        return br;
    }
    public static BatchRequest createChargeRequest(final Long requestId, final Long reference) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans/$.loanId/charges");
        br.setMethod("POST");
        br.setReference(reference);
        final String body = "{\"chargeId\": \"2\", \"locale\": \"en\", \"amount\": \"100\", "
                + "\"dateFormat\": \"dd MMMM yyyy\", \"dueDate\": \"29 April 2013\"}";
        br.setBody(body);
        return br;
    }
    public static BatchRequest collectChargesRequest(final Long requestId, final Long reference) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans/$.loanId/charges");
        br.setReference(reference);
        br.setMethod("GET");
        br.setBody("{ }");
        return br;
    }
    public static BatchRequest activateClientRequest(final Long requestId, final Long reference) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("clients/$.clientId?command=activate");
        br.setReference(reference);
        br.setMethod("POST");
        br.setBody("{\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", \"activationDate\": \"01 March 2011\"}");
        return br;
    }
    public static BatchRequest approveLoanRequest(final Long requestId, final Long reference) {
        return approveLoanRequest(requestId, reference, LocalDate.now(ZoneId.systemDefault()).minusDays(10));
    }
    public static BatchRequest approveLoanRequest(final Long requestId, final Long reference, LocalDate date) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans/$.loanId?command=approve");
        br.setReference(reference);
        br.setMethod("POST");
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        br.setBody("{\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", \"approvedOnDate\": \"" + dateString + "\","
                + "\"note\": \"Loan approval note\"}");
        return br;
    }
    public static BatchRequest disburseLoanRequest(final Long requestId, final Long reference) {
        return disburseLoanRequest(requestId, reference, LocalDate.now(ZoneId.systemDefault()).minusDays(8));
    }
    public static BatchRequest disburseLoanRequest(final Long requestId, final Long reference, final LocalDate date) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setRelativeUrl("loans/$.loanId?command=disburse");
        br.setReference(reference);
        br.setMethod("POST");
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        br.setBody("{\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", \"actualDisbursementDate\": \"" + dateString + "\"}");
        return br;
    }
    public static BatchRequest repayLoanRequest(final Long requestId, final Long reference, final String amount) {
        return repayLoanRequest(requestId, reference, amount, LocalDate.now(ZoneId.systemDefault()));
    }
    public static BatchRequest repayLoanRequest(final Long requestId, final Long reference, final String amount, final LocalDate date) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setReference(reference);
        br.setRelativeUrl("loans/$.loanId/transactions?command=repayment");
        br.setMethod("POST");
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        br.setBody(String.format(
                "{\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", " + "\"transactionDate\": \"%s\",  \"transactionAmount\": %s}",
                dateString, amount));
        return br;
    }
    public static BatchRequest creditBalanceRefundRequest(final Long requestId, final Long reference, final String amount) {
        return creditBalanceRefundRequest(requestId, reference, amount, LocalDate.now(ZoneId.systemDefault()));
    }
    public static BatchRequest creditBalanceRefundRequest(final Long requestId, final Long reference, final String amount, LocalDate date) {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(requestId);
        br.setReference(reference);
        br.setRelativeUrl("loans/$.loanId/transactions?command=creditBalanceRefund");
        br.setMethod("POST");
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
        br.setBody(String.format(
                "{\"locale\": \"en\", \"dateFormat\": \"dd MMMM yyyy\", " + "\"transactionDate\": \"%s\",  \"transactionAmount\": %s}",
                dateString, amount));
        return br;
    }
    public static void verifyClientCreatedOnServer(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String externalId) {
        LOG.info("------------------------------CHECK CLIENT DETAILS------------------------------------\n");
        final String CLIENT_URL = "/fineract-provider/api/v1/clients?externalId=" + externalId + "&" + Utils.TENANT_IDENTIFIER;
        final Integer responseRecords = Utils.performServerGet(requestSpec, responseSpec, CLIENT_URL, "totalFilteredRecords");
        Assertions.assertEquals((long) 0, (long) responseRecords, "No records found with given externalId");
    }
    public static BatchRequest getTransactionByIdRequest(final Long requestId, final Long reference) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = "loans/$.loanId/transactions/$.resourceId";
        br.setRequestId(requestId);
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.GET);
        br.setReference(reference);
        br.setBody("{}");
        return br;
    }
    public static BatchRequest getLoanByIdRequest(final Long requestId, final Long reference, final String queryParameter) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = "loans/$.loanId";
        if (queryParameter != null) {
            relativeUrl = relativeUrl + "?" + queryParameter;
        }
        br.setRequestId(requestId);
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.GET);
        br.setReference(reference);
        br.setBody("{}");
        return br;
    }
    public static BatchRequest getLoanByIdRequest(final Long loanId, final String queryParameter) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = String.format("loans/%s", loanId);
        if (queryParameter != null) {
            relativeUrl = relativeUrl + "?" + queryParameter;
        }
        br.setRequestId(4567L);
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.GET);
        br.setBody("{}");
        return br;
    }
    public static BatchRequest getDatatableByIdRequest(final Long loanId, final String datatableName, final String queryParameter) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = String.format("datatables/%s/%s", datatableName, loanId);
        if (queryParameter != null) {
            relativeUrl = relativeUrl + "?" + queryParameter;
        }
        br.setRequestId(4568L);
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.GET);
        br.setBody("{}");
        return br;
    }
}
