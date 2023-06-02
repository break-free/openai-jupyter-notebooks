
package org.apache.fineract.batch.command.internal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.api.LoanTransactionsApiResource;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class CreateTransactionLoanCommandStrategyTest {
    @Test
    public void testExecuteSuccessScenario() {
        final TestContext testContext = new TestContext();
        final Long loanId = Long.valueOf(RandomStringUtils.randomNumeric(4));
        final String command = "myCommand";
        final BatchRequest batchRequest = getBatchRequest(loanId, command);
        final String responseBody = "myResponseBody";
        when(testContext.loanTransactionsApiResource.executeLoanTransaction(loanId, command, batchRequest.getBody()))
                .thenReturn(responseBody);
        BatchResponse batchResponse = testContext.subjectToTest.execute(batchRequest, testContext.uriInfo);
        assertEquals(HttpStatus.SC_OK, batchResponse.getStatusCode());
        assertSame(responseBody, batchResponse.getBody());
        assertEquals(batchRequest.getRequestId(), batchResponse.getRequestId());
        assertEquals(batchRequest.getHeaders(), batchResponse.getHeaders());
        verify(testContext.loanTransactionsApiResource).executeLoanTransaction(loanId, command, batchRequest.getBody());
    }
    private BatchRequest getBatchRequest(final Long loanId, final String command) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = "loans/" + loanId + "/transactions?command=" + command;
        br.setRequestId(Long.valueOf(RandomStringUtils.randomNumeric(5)));
        br.setRelativeUrl(relativeUrl);
        br.setMethod(HttpMethod.POST);
        br.setReference(Long.valueOf(RandomStringUtils.randomNumeric(5)));
        br.setBody("{}");
        return br;
    }
    private static class TestContext {
        @Mock
        private UriInfo uriInfo;
        @Mock
        private LoanTransactionsApiResource loanTransactionsApiResource;
        private final CreateTransactionLoanCommandStrategy subjectToTest;
        TestContext() {
            MockitoAnnotations.openMocks(this);
            subjectToTest = new CreateTransactionLoanCommandStrategy(loanTransactionsApiResource);
        }
    }
}
