
package org.apache.fineract.batch.command.internal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import java.util.stream.Stream;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.api.LoanTransactionsApiResource;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class GetTransactionByIdCommandStrategyTest {
    private static Stream<Arguments> provideQueryParameters() {
        return Stream.of(Arguments.of(null, 0), Arguments.of("id,date,amount", 1));
    }
    @ParameterizedTest
    @MethodSource("provideQueryParameters")
    public void testExecuteSuccessScenario(final String fields, final int noOfQueryParams) {
        final TestContext testContext = new TestContext();
        final Long loanId = Long.valueOf(RandomStringUtils.randomNumeric(4));
        final Long transactionId = Long.valueOf(RandomStringUtils.randomNumeric(4));
        final BatchRequest request = getBatchRequest(loanId, transactionId, fields);
        final String responseBody = "{\"id\":12,\"officeId\":1,\"officeName\":\"Head Office\",\"type\":{\"id\":10,\"code\":"
                + "\"loanTransactionType.accrual\",\"value\":\"Accrual\",\"disbursement\":false,\"repaymentAtDisbursement\":false,"
                + "\"repayment\":false,\"contra\":false,\"waiveInterest\":false,\"waiveCharges\":false,\"accrual\":true,\"writeOff\":false,"
                + "\"recoveryRepayment\":false,\"initiateTransfer\":false,\"approveTransfer\":false,\"withdrawTransfer\":false,"
                + "\"rejectTransfer\":false,\"chargePayment\":false,\"refund\":false,\"refundForActiveLoans\":false},\"date\":[2022,3,29],"
                + "\"currency\":{\"code\":\"EUR\",\"name\":\"Euro\",\"decimalPlaces\":2,\"inMultiplesOf\":0,\"displaySymbol\":\"€\","
                + "\"nameCode\":\"currency.EUR\",\"displayLabel\":\"Euro (€)\"},\"amount\":0.000000,\"netDisbursalAmount\":200.000000,"
                + "\"principalPortion\":0,\"interestPortion\":0.000000,\"feeChargesPortion\":0,\"penaltyChargesPortion\":0,\"overpaymentPortion\":0,"
                + "\"unrecognizedIncomePortion\":0,\"outstandingLoanBalance\":0,\"submittedOnDate\":[2022,3,29],\"manuallyReversed\":false,"
                + "\"loanChargePaidByList\":[],\"numberOfRepayments\":0}";
        given(testContext.loanTransactionsApiResource.retrieveTransaction(eq(loanId), eq(transactionId), eq(fields), any(UriInfo.class)))
                .willReturn(responseBody);
        final BatchResponse response = testContext.subjectToTest.execute(request, testContext.uriInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getRequestId()).isEqualTo(request.getRequestId());
        assertThat(response.getHeaders()).isEqualTo(request.getHeaders());
        assertThat(response.getBody()).isEqualTo(responseBody);
    }
    private BatchRequest getBatchRequest(final Long loanId, final Long transactionId, final String fields) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = "loans/" + loanId + "/transactions/" + transactionId;
        if (fields != null) {
            relativeUrl = relativeUrl + "?fields=" + fields;
        }
        br.setRequestId(Long.valueOf(RandomStringUtils.randomNumeric(5)));
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.GET);
        br.setReference(Long.valueOf(RandomStringUtils.randomNumeric(5)));
        br.setBody("{}");
        return br;
    }
    private static class TestContext {
        @Mock
        private UriInfo uriInfo;
        @Mock
        private LoanTransactionsApiResource loanTransactionsApiResource;
        private final GetTransactionByIdCommandStrategy subjectToTest;
        TestContext() {
            MockitoAnnotations.openMocks(this);
            subjectToTest = new GetTransactionByIdCommandStrategy(loanTransactionsApiResource);
        }
    }
}
