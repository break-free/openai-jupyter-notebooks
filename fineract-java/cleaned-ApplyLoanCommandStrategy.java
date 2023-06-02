
package org.apache.fineract.batch.command.internal;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.api.LoansApiResource;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ApplyLoanCommandStrategy implements CommandStrategy {
    private final LoansApiResource loansApiResource;
    @Override
    public BatchResponse execute(BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        responseBody = loansApiResource.calculateLoanScheduleOrSubmitLoanApplication(null, null, request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
