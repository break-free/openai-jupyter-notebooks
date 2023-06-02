
package org.apache.fineract.integrationtests;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.integrationtests.common.BatchHelper;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.GroupHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BatchRequestsIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(BatchRequestsIntegrationTest.class);
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private static final SecureRandom secureRandom = new SecureRandom();
    public BatchRequestsIntegrationTest() {
    }
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @Test
    public void shouldReturnOkStatusForLoansAppliedToSelectedClients() {
        final Integer clientsCount = (int) Math.ceil(secureRandom.nextDouble() * 7) + 3;
        final Integer[] clientIDs = new Integer[clientsCount];
        Integer groupID = GroupHelper.createGroup(this.requestSpec, this.responseSpec, true);
        for (Integer i = 0; i < clientsCount; i++) {
            clientIDs[i] = ClientHelper.createClient(this.requestSpec, this.responseSpec);
            groupID = GroupHelper.associateClient(this.requestSpec, this.responseSpec, groupID.toString(), clientIDs[i].toString());
            LOG.info("client {} has been added to the group {}", clientIDs[i], groupID);
        }
        final Integer loansCount = (int) Math.ceil(secureRandom.nextDouble() * 4) + 1;
        final Integer[] loanProducts = new Integer[loansCount];
        for (Integer i = 0; i < loansCount; i++) {
            final String loanProductJSON = new LoanProductTestBuilder() 
                    .withPrincipal(String.valueOf(10000.00 + Math.ceil(secureRandom.nextDouble() * 1000000.00))) 
                    .withNumberOfRepayments(String.valueOf(2 + (int) Math.ceil(secureRandom.nextDouble() * 36))) 
                    .withRepaymentAfterEvery(String.valueOf(1 + (int) Math.ceil(secureRandom.nextDouble() * 3))) 
                    .withRepaymentTypeAsMonth() 
                    .withinterestRatePerPeriod(String.valueOf(1 + (int) Math.ceil(secureRandom.nextDouble() * 4))) 
                    .withInterestRateFrequencyTypeAsMonths() 
                    .withAmortizationTypeAsEqualPrincipalPayment() 
                    .withInterestTypeAsDecliningBalance() 
                    .currencyDetails("0", "100").build(null);
            loanProducts[i] = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        }
        final Integer loanProductID = loanProducts[(int) Math.floor(secureRandom.nextDouble() * (loansCount - 1))];
        final List<BatchRequest> batchRequests = new ArrayList<>();
        Integer selClientsCount = (int) Math.ceil(secureRandom.nextDouble() * clientsCount) + 2;
        for (int i = 0; i < selClientsCount; i++) {
            final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
            Assertions.assertNotNull(collateralId);
            final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                    String.valueOf(clientIDs[(int) Math.floor(secureRandom.nextDouble() * (clientsCount - 1))]), collateralId);
            Assertions.assertNotNull(clientCollateralId);
            BatchRequest br = BatchHelper.applyLoanRequest((long) selClientsCount, null, loanProductID, clientCollateralId);
            br.setBody(br.getBody().replace("$.clientId",
                    String.valueOf(clientIDs[(int) Math.floor(secureRandom.nextDouble() * (clientsCount - 1))])));
            batchRequests.add(br);
        }
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        for (BatchResponse res : response) {
            Assertions.assertEquals(200L, (long) res.getStatusCode(), "Verify Status Code 200");
        }
    }
}
