
package org.apache.fineract.batch.command.internal;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.savings.api.SavingsAccountsApiResource;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ApplySavingsCommandStrategy implements CommandStrategy {
    private final SavingsAccountsApiResource savingsAccountsApiResource;
    @Override
    public BatchResponse execute(BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        responseBody = savingsAccountsApiResource.submitApplication(request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
