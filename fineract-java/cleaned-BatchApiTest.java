
package org.apache.fineract.integrationtests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.fineract.batch.command.internal.CreateTransactionLoanCommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.integrationtests.common.BatchHelper;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.CollateralManagementHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.fineract.integrationtests.common.loans.LoanProductTestBuilder;
import org.apache.fineract.integrationtests.common.loans.LoanTransactionHelper;
import org.apache.fineract.integrationtests.common.savings.SavingsProductHelper;
import org.apache.fineract.integrationtests.common.system.DatatableHelper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanStatus;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class BatchApiTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    private DatatableHelper datatableHelper;
    private static final String LOAN_APP_TABLE_NAME = "m_loan";
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.datatableHelper = new DatatableHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void shouldReturnStatusNotImplementedUnknownCommand() {
        final BatchRequest br = new BatchRequest();
        br.setRequestId(4711L);
        br.setRelativeUrl("/nirvana");
        br.setMethod("POST");
        final List<BatchResponse> response = BatchHelper.postWithSingleRequest(this.requestSpec, this.responseSpec, br);
        for (BatchResponse resp : response) {
            Assertions.assertEquals((long) 501, (long) resp.getStatusCode(), "Verify Status code 501");
        }
    }
    @Test
    public void shouldReturnOkStatusForCreateClientCommand() {
        final BatchRequest br = BatchHelper.createClientRequest(4712L, "");
        final List<BatchResponse> response = BatchHelper.postWithSingleRequest(this.requestSpec, this.responseSpec, br);
        for (BatchResponse resp : response) {
            Assertions.assertEquals((long) 200, (long) resp.getStatusCode(), "Verify Status code 200");
        }
    }
    @Test
    public void shouldRollBackAllTransactionsOnFailure() {
        final BatchRequest br1 = BatchHelper.createClientRequest(4713L, "TestExtId11");
        final BatchRequest br2 = BatchHelper.createClientRequest(4714L, "TestExtId12");
        final BatchRequest br3 = BatchHelper.createClientRequest(4715L, "TestExtId11");
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        BatchHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, "TestExtId11");
        BatchHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, "TestExtId12");
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals((long) 400, (long) response.get(0).getStatusCode(), "Verify Status code 400");
    }
    @Test
    public void shouldReflectChangesOnClientUpdate() {
        final BatchRequest br1 = BatchHelper.createClientRequest(4716L, "");
        final BatchRequest br2 = BatchHelper.updateClientRequest(4717L, 4716L);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        final JsonObject changes = new FromJsonHelper().parse(response.get(1).getBody()).getAsJsonObject().get("changes").getAsJsonObject();
        Assertions.assertEquals("TestFirstName", changes.get("firstname").getAsString());
        Assertions.assertEquals("TestLastName", changes.get("lastname").getAsString());
    }
    @Test
    public void shouldReturnOkStatusForApplyLoanCommand() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest br1 = BatchHelper.createClientRequest(4718L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4719L, 4718L);
        final BatchRequest br3 = BatchHelper.applyLoanRequest(4720L, 4719L, productId, clientCollateralId);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        final JsonElement clientId = new FromJsonHelper().parse(response.get(0).getBody()).getAsJsonObject().get("clientId");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(1).getStatusCode(),
                "Verify Status Code 200" + clientId.getAsString());
    }
    @Test
    public void shouldReturnOkStatusForApplySavingsCommand() {
        final SavingsProductHelper savingsProductHelper = new SavingsProductHelper();
        final String savingsProductJSON = savingsProductHelper 
                .withInterestCompoundingPeriodTypeAsDaily() 
                .withInterestPostingPeriodTypeAsMonthly() 
                .withInterestCalculationPeriodTypeAsDailyBalance() 
                .withMinimumOpenningBalance("5000").build();
        final Integer productId = SavingsProductHelper.createSavingsProduct(savingsProductJSON, this.requestSpec, this.responseSpec);
        final BatchRequest br1 = BatchHelper.createClientRequest(4720L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4721L, 4720L);
        final BatchRequest br3 = BatchHelper.applySavingsRequest(4722L, 4721L, productId);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(1).getStatusCode(), "Verify Status Code 200");
    }
    @Test
    public void shouldReturnOkStatusForCollectChargesCommand() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest br1 = BatchHelper.createClientRequest(4722L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4723L, 4722L);
        final BatchRequest br3 = BatchHelper.applyLoanRequest(4724L, 4723L, productId, clientCollateralId);
        final BatchRequest br4 = BatchHelper.collectChargesRequest(4725L, 4724L);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        batchRequests.add(br4);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(3).getStatusCode(), "Verify Status Code 200 for Create Loan Charge");
    }
    @Test
    public void shouldReturnOkStatusForBatchRepayment() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                clientID.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest br1 = BatchHelper.createClientRequest(4730L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4731L, 4730L);
        final BatchRequest br3 = BatchHelper.applyLoanRequest(4732L, 4731L, productId, clientCollateralId);
        final BatchRequest br4 = BatchHelper.approveLoanRequest(4733L, 4732L);
        final BatchRequest br5 = BatchHelper.disburseLoanRequest(4734L, 4733L);
        final BatchRequest br6 = BatchHelper.repayLoanRequest(4735L, 4734L, "500");
        final BatchRequest br7 = BatchHelper.repayLoanRequest(4736L, 4734L, "500");
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        batchRequests.add(br4);
        batchRequests.add(br5);
        batchRequests.add(br6);
        batchRequests.add(br7);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(5).getStatusCode(), "Verify Status Code 200 for Repayment");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(6).getStatusCode(), "Verify Status Code 200 for Repayment");
    }
    @Test
    public void shouldReturnOkStatusForBatchCreditBalanceRefund() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("1000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                clientID.toString(), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final Long createActiveClientRequestId = 4730L;
        final Long applyLoanRequestId = createActiveClientRequestId + 1;
        final Long approveLoanRequestId = applyLoanRequestId + 1;
        final Long disburseLoanRequestId = approveLoanRequestId + 1;
        final Long repayLoanRequestId = disburseLoanRequestId + 1;
        final Long creditBalanceRefundRequestId = repayLoanRequestId + 1;
        final BatchRequest br1 = BatchHelper.createActiveClientRequest(createActiveClientRequestId, "");
        final BatchRequest br2 = BatchHelper.applyLoanRequest(applyLoanRequestId, createActiveClientRequestId, productId,
                clientCollateralId);
        final BatchRequest br3 = BatchHelper.approveLoanRequest(approveLoanRequestId, applyLoanRequestId);
        final BatchRequest br4 = BatchHelper.disburseLoanRequest(disburseLoanRequestId, approveLoanRequestId);
        final BatchRequest br5 = BatchHelper.repayLoanRequest(repayLoanRequestId, disburseLoanRequestId, "20000");
        final BatchRequest br6 = BatchHelper.creditBalanceRefundRequest(creditBalanceRefundRequestId, repayLoanRequestId, "500");
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        batchRequests.add(br4);
        batchRequests.add(br5);
        batchRequests.add(br6);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(4).getStatusCode(), "Verify Status Code 200 for Repayment");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(5).getStatusCode(),
                "Verify Status Code 200 for Credit Balance Refund");
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulClientActivation() {
        final BatchRequest br1 = BatchHelper.createClientRequest(4726L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4727L, 4726L);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(0).getStatusCode(), "Verify Status Code 200 for Create Client");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(1).getStatusCode(), "Verify Status Code 200 for Activate Client");
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulLoanApprovalAndDisburse() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest br1 = BatchHelper.createClientRequest(4730L, "");
        final BatchRequest br2 = BatchHelper.activateClientRequest(4731L, 4730L);
        final BatchRequest br3 = BatchHelper.applyLoanRequest(4732L, 4731L, productId, clientCollateralId);
        final BatchRequest br4 = BatchHelper.approveLoanRequest(4733L, 4732L);
        final BatchRequest br5 = BatchHelper.disburseLoanRequest(4734L, 4733L);
        final List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(br1);
        batchRequests.add(br2);
        batchRequests.add(br3);
        batchRequests.add(br4);
        batchRequests.add(br5);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(3).getStatusCode(), "Verify Status Code 200 for Approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(4).getStatusCode(), "Verify Status Code 200 for Disburse Loan");
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulLoanApprovalAndDisburseWithTransaction() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Integer clientID = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientID);
        final Integer collateralId = CollateralManagementHelper.createCollateralProduct(this.requestSpec, this.responseSpec);
        Assertions.assertNotNull(collateralId);
        final Integer clientCollateralId = CollateralManagementHelper.createClientCollateral(this.requestSpec, this.responseSpec,
                String.valueOf(clientID), collateralId);
        Assertions.assertNotNull(clientCollateralId);
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest br1 = BatchHelper.createActiveClientRequest(4740L, "");
        final BatchRequest br2 = BatchHelper.applyLoanRequest(4742L, 4740L, productId, clientCollateralId);
        final BatchRequest br3 = BatchHelper.approveLoanRequest(4743L, 4742L);
        final BatchRequest br4 = BatchHelper.disburseLoanRequest(4744L, 4743L);
        final List<BatchRequest> batchRequests = Arrays.asList(br1, br2, br3, br4);
        final String jsonifiedRequest = BatchHelper.toJsonString(batchRequests);
        final List<BatchResponse> response = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec, this.responseSpec,
                jsonifiedRequest);
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(0).getStatusCode(), "Verify Status Code 200 for create client");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(1).getStatusCode(), "Verify Status Code 200 for apply Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(2).getStatusCode(), "Verify Status Code 200 for approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, (long) response.get(3).getStatusCode(), "Verify Status Code 200 for disburse Loan");
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulDisbursementAndGetTransaction() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Long applyLoanRequestId = 6730L;
        final Long approveLoanRequestId = 6731L;
        final Long disburseLoanRequestId = 6732L;
        final Long getTransactionRequestId = 6733L;
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final Integer clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientId);
        final BatchRequest batchRequest1 = BatchHelper.applyLoanRequestWithClientId(applyLoanRequestId, clientId, productId);
        final BatchRequest batchRequest2 = BatchHelper.approveLoanRequest(approveLoanRequestId, applyLoanRequestId);
        final BatchRequest batchRequest3 = BatchHelper.disburseLoanRequest(disburseLoanRequestId, approveLoanRequestId);
        final BatchRequest batchRequest4 = BatchHelper.getTransactionByIdRequest(getTransactionRequestId, disburseLoanRequestId);
        final List<BatchRequest> batchRequests = Arrays.asList(batchRequest1, batchRequest2, batchRequest3, batchRequest4);
        final List<BatchResponse> responses = BatchHelper.postBatchRequestsWithoutEnclosingTransaction(this.requestSpec, this.responseSpec,
                BatchHelper.toJsonString(batchRequests));
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(0).getStatusCode(), "Verify Status Code 200 for Apply Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(1).getStatusCode(), "Verify Status Code 200 for Approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(2).getStatusCode(), "Verify Status Code 200 for Disburse Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(3).getStatusCode(), "Verify Status Code 200 for Get Transaction By Id");
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulCreateClientCreateApproveAndGetLoan() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Long createActiveClientRequestId = 4730L;
        final Long applyLoanRequestId = 4731L;
        final Long approveLoanRequestId = 4732L;
        final Long getLoanByIdRequestId = 4733L;
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final BatchRequest batchRequest1 = BatchHelper.createActiveClientRequest(createActiveClientRequestId, "");
        final BatchRequest batchRequest2 = BatchHelper.applyLoanRequest(applyLoanRequestId, createActiveClientRequestId, productId, null);
        final BatchRequest batchRequest3 = BatchHelper.approveLoanRequest(approveLoanRequestId, applyLoanRequestId);
        final BatchRequest batchRequest4 = BatchHelper.getLoanByIdRequest(getLoanByIdRequestId, applyLoanRequestId, null);
        final List<BatchRequest> batchRequests = Arrays.asList(batchRequest1, batchRequest2, batchRequest3, batchRequest4);
        final List<BatchResponse> responses = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec, this.responseSpec,
                BatchHelper.toJsonString(batchRequests));
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(0).getStatusCode(), "Verify Status Code 200 for Create Client");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(1).getStatusCode(), "Verify Status Code 200 for Apply Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(2).getStatusCode(), "Verify Status Code 200 for Approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(3).getStatusCode(), "Verify Status Code 200 for Get Loan By Id");
        final FromJsonHelper jsonHelper = new FromJsonHelper();
        final Long loanId = jsonHelper.extractLongNamed("loanId", jsonHelper.parse(responses.get(1).getBody()).getAsJsonObject());
        final Long loanIdInGetResponse = jsonHelper.extractLongNamed("id", jsonHelper.parse(responses.get(3).getBody()).getAsJsonObject());
        final JsonObject statusInGetResponse = jsonHelper.parse(responses.get(3).getBody()).getAsJsonObject().get("status")
                .getAsJsonObject();
        Assertions.assertEquals(loanId, loanIdInGetResponse);
        Assertions.assertEquals(LoanStatus.APPROVED.getCode(), jsonHelper.extractStringNamed("code", statusInGetResponse));
        Assertions.assertEquals("Approved", jsonHelper.extractStringNamed("value", statusInGetResponse));
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulCreateApproveAndGetLoan() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Long applyLoanRequestId = 5730L;
        final Long approveLoanRequestId = 5731L;
        final Long getLoanByIdRequestId = 5732L;
        final Long getLoanByIdWithQueryParametersRequestId = 5733L;
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final Integer clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientId);
        final BatchRequest batchRequest1 = BatchHelper.applyLoanRequestWithClientId(applyLoanRequestId, clientId, productId);
        final BatchRequest batchRequest2 = BatchHelper.approveLoanRequest(approveLoanRequestId, applyLoanRequestId);
        final BatchRequest batchRequest3 = BatchHelper.getLoanByIdRequest(getLoanByIdRequestId, applyLoanRequestId, null);
        final BatchRequest batchRequest4 = BatchHelper.getLoanByIdRequest(getLoanByIdWithQueryParametersRequestId, applyLoanRequestId,
                "associations=repaymentSchedule,transactions");
        final List<BatchRequest> batchRequests = Arrays.asList(batchRequest1, batchRequest2, batchRequest3, batchRequest4);
        final List<BatchResponse> responses = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec, this.responseSpec,
                BatchHelper.toJsonString(batchRequests));
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(0).getStatusCode(), "Verify Status Code 200 for Apply Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(1).getStatusCode(), "Verify Status Code 200 for Approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(2).getStatusCode(),
                "Verify Status Code 200 for Get Loan By Id without query parameter");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(3).getStatusCode(),
                "Verify Status Code 200 for Get Loan By Id with query parameter");
        final FromJsonHelper jsonHelper = new FromJsonHelper();
        final Long loanId = jsonHelper.extractLongNamed("loanId", jsonHelper.parse(responses.get(0).getBody()).getAsJsonObject());
        final Long loanIdInGetResponse = jsonHelper.extractLongNamed("id", jsonHelper.parse(responses.get(2).getBody()).getAsJsonObject());
        final JsonObject statusInGetResponse = jsonHelper.parse(responses.get(2).getBody()).getAsJsonObject().get("status")
                .getAsJsonObject();
        Assertions.assertEquals(loanId, loanIdInGetResponse);
        Assertions.assertEquals(LoanStatus.APPROVED.getCode(), jsonHelper.extractStringNamed("code", statusInGetResponse));
        Assertions.assertEquals("Approved", jsonHelper.extractStringNamed("value", statusInGetResponse));
        Assertions.assertFalse(responses.get(2).getBody().contains("repaymentSchedule"));
        Assertions.assertTrue(responses.get(3).getBody().contains("repaymentSchedule"));
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulGetDataTableEntry() {
        final FromJsonHelper jsonHelper = new FromJsonHelper();
        final Long loanId = jsonHelper.extractLongNamed("loanId", jsonHelper.parse(setupAccount()).getAsJsonObject());
        final String datatableName = this.datatableHelper.createDatatable(LOAN_APP_TABLE_NAME, false);
        try {
            final BatchRequest getLoanBatchRequest = BatchHelper.getLoanByIdRequest(loanId, "associations=repaymentSchedule,transactions");
            final BatchRequest getDatatableBatchRequest = BatchHelper.getDatatableByIdRequest(loanId, datatableName,
                    "genericResultSet=true");
            final List<BatchRequest> batchRequestsGetLoan = Arrays.asList(getLoanBatchRequest, getDatatableBatchRequest);
            final List<BatchResponse> responsesGetLoan = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec,
                    this.responseSpec, BatchHelper.toJsonString(batchRequestsGetLoan));
            final String getLoanResponse = responsesGetLoan.get(0).getBody();
            final String getDatatableResponse = responsesGetLoan.get(1).getBody();
            Assertions.assertEquals(HttpStatus.SC_OK, responsesGetLoan.get(0).getStatusCode(), "Verify Status Code 200 for get loan");
            Assertions.assertEquals(HttpStatus.SC_OK, responsesGetLoan.get(1).getStatusCode(), "Verify Status Code 200 for datatable");
            final Long loanIdInGetResponse = jsonHelper.extractLongNamed("id", jsonHelper.parse(getLoanResponse).getAsJsonObject());
            Assertions.assertEquals(loanId, loanIdInGetResponse);
            Assertions.assertTrue(getLoanResponse.contains("repaymentSchedule"));
            Assertions.assertTrue(getLoanResponse.contains("transactions"));
            Assertions.assertTrue(getDatatableResponse.contains("columnHeaders"));
            Assertions.assertTrue(getDatatableResponse.contains("data"));
        } finally {
            deleteDatatable(datatableName);
        }
    }
    @Test
    public void shouldReturnOkStatusOnSuccessfulGetDatatableEntryWithNoQueryParam() {
        final FromJsonHelper jsonHelper = new FromJsonHelper();
        final Long loanId = jsonHelper.extractLongNamed("loanId", jsonHelper.parse(setupAccount()).getAsJsonObject());
        final String datatableName = this.datatableHelper.createDatatable(LOAN_APP_TABLE_NAME, false);
        try {
            final BatchRequest getLoanBatchRequest = BatchHelper.getLoanByIdRequest(loanId, "associations=repaymentSchedule,transactions");
            final BatchRequest getDatatableBatchRequest = BatchHelper.getDatatableByIdRequest(loanId, datatableName, null);
            final List<BatchRequest> batchRequestsGetLoan = Arrays.asList(getLoanBatchRequest, getDatatableBatchRequest);
            final List<BatchResponse> responsesGetLoan = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec,
                    this.responseSpec, BatchHelper.toJsonString(batchRequestsGetLoan));
            final String getLoanResponse = responsesGetLoan.get(0).getBody();
            Assertions.assertEquals(HttpStatus.SC_OK, responsesGetLoan.get(0).getStatusCode(), "Verify Status Code 200 for Get Loan");
            Assertions.assertEquals(HttpStatus.SC_OK, responsesGetLoan.get(1).getStatusCode(), "Verify Status Code 200 for Get Datatable");
            final Long loanIdInGetResponse = jsonHelper.extractLongNamed("id", jsonHelper.parse(getLoanResponse).getAsJsonObject());
            Assertions.assertEquals(loanId, loanIdInGetResponse);
            Assertions.assertTrue(getLoanResponse.contains("repaymentSchedule"));
            Assertions.assertTrue(getLoanResponse.contains("transactions"));
        } finally {
            deleteDatatable(datatableName);
        }
    }
    private void deleteDatatable(final String datatableName) {
        String deletedDatatableName = this.datatableHelper.deleteDatatable(datatableName);
        assertEquals(datatableName, deletedDatatableName, "Fail to delete the datatable");
    }
    private String setupAccount() {
        final String loanProductJSON = new LoanProductTestBuilder() 
                .withPrincipal("10000000.00") 
                .withNumberOfRepayments("24") 
                .withRepaymentAfterEvery("1") 
                .withRepaymentTypeAsMonth() 
                .withinterestRatePerPeriod("2") 
                .withInterestRateFrequencyTypeAsMonths() 
                .withAmortizationTypeAsEqualPrincipalPayment() 
                .withInterestTypeAsDecliningBalance() 
                .currencyDetails("0", "100").build(null);
        final Long applyLoanRequestId = 5730L;
        final Long approveLoanRequestId = 5731L;
        final Long disburseLoanRequestId = 5734L;
        final Long repayLoanRequestId = 5735L;
        final Integer productId = new LoanTransactionHelper(this.requestSpec, this.responseSpec).getLoanProductId(loanProductJSON);
        final Integer clientId = ClientHelper.createClient(this.requestSpec, this.responseSpec);
        ClientHelper.verifyClientCreatedOnServer(this.requestSpec, this.responseSpec, clientId);
        final BatchRequest applyLoanBatchRequest = BatchHelper.applyLoanRequestWithClientId(applyLoanRequestId, clientId, productId);
        final BatchRequest approveLoanBatchRequest = BatchHelper.approveLoanRequest(approveLoanRequestId, applyLoanRequestId);
        final BatchRequest disburseLoanBatchRequest = BatchHelper.disburseLoanRequest(disburseLoanRequestId, applyLoanRequestId);
        final BatchRequest repaymentBatchRequest = BatchHelper.repayLoanRequest(repayLoanRequestId, applyLoanRequestId, "500");
        final List<BatchRequest> batchRequests = Arrays.asList(applyLoanBatchRequest, approveLoanBatchRequest, disburseLoanBatchRequest,
                repaymentBatchRequest);
        final List<BatchResponse> responses = BatchHelper.postBatchRequestsWithEnclosingTransaction(this.requestSpec, this.responseSpec,
                BatchHelper.toJsonString(batchRequests));
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(0).getStatusCode(), "Verify Status Code 200 for Apply Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(1).getStatusCode(), "Verify Status Code 200 for Approve Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(2).getStatusCode(), "Verify Status Code 200 for Disburse Loan");
        Assertions.assertEquals(HttpStatus.SC_OK, responses.get(3).getStatusCode(), "Verify Status Code 200 for Repay Loan");
        return responses.get(0).getBody();
    }
}
