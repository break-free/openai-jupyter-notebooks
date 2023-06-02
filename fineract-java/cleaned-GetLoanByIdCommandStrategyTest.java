
package org.apache.fineract.batch.command.internal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.api.MutableUriInfo;
import org.apache.fineract.portfolio.loanaccount.api.LoansApiResource;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
public class GetLoanByIdCommandStrategyTest {
    private static Stream<Arguments> provideQueryParameters() {
        return Stream.of(Arguments.of(null, null, null, 0), Arguments.of("all", null, null, 1),
                Arguments.of("repaymentSchedule,transactions", null, "guarantors,futureSchedule", 2),
                Arguments.of("repaymentSchedule,transactions", "id,principal,annualInterestRate", null, 2),
                Arguments.of("repaymentSchedule,transactions", "id,principal,annualInterestRate", "guarantors,futureSchedule", 3));
    }
    @ParameterizedTest
    @MethodSource("provideQueryParameters")
    public void testExecuteSuccessScenario(final String associations, final String fields, final String exclude,
            final int noOfQueryParams) {
        final TestContext testContext = new TestContext();
        final Long loanId = Long.valueOf(RandomStringUtils.randomNumeric(4));
        final BatchRequest request = getBatchRequest(loanId, associations, exclude, fields);
        final String responseBody = "{\\\"id\\\":2,\\\"accountNo\\\":\\\"000000002\\\"}";
        given(testContext.loansApiResource.retrieveLoan(eq(loanId), eq(false), eq(associations), eq(exclude), eq(fields),
                any(UriInfo.class))).willReturn(responseBody);
        final BatchResponse response = testContext.underTest.execute(request, testContext.uriInfo);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getRequestId()).isEqualTo(request.getRequestId());
        assertThat(response.getHeaders()).isEqualTo(request.getHeaders());
        assertThat(response.getBody()).isEqualTo(responseBody);
        verify(testContext.loansApiResource).retrieveLoan(eq(loanId), eq(false), eq(associations), eq(exclude), eq(fields),
                testContext.uriInfoCaptor.capture());
        MutableUriInfo mutableUriInfo = testContext.uriInfoCaptor.getValue();
        assertThat(mutableUriInfo.getAdditionalQueryParameters()).hasSize(noOfQueryParams);
    }
    private BatchRequest getBatchRequest(final Long loanId, final String associations, final String exclude, final String fields) {
        final BatchRequest br = new BatchRequest();
        String relativeUrl = "loans/" + loanId;
        Set<String> queryParams = new HashSet<>();
        if (associations != null) {
            queryParams.add("associations=" + associations);
        }
        if (exclude != null) {
            queryParams.add("exclude=" + exclude);
        }
        if (fields != null) {
            queryParams.add("fields=" + fields);
        }
        if (!queryParams.isEmpty()) {
            relativeUrl = relativeUrl + "?" + String.join("&", queryParams);
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
        private LoansApiResource loansApiResource;
        @Captor
        private ArgumentCaptor<MutableUriInfo> uriInfoCaptor;
        private final GetLoanByIdCommandStrategy underTest;
        TestContext() {
            MockitoAnnotations.openMocks(this);
            underTest = new GetLoanByIdCommandStrategy(loansApiResource);
        }
    }
}
