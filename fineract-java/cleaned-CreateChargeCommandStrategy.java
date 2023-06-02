
package org.apache.fineract.batch.command.internal;
import com.google.common.base.Splitter;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.api.LoanChargesApiResource;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CreateChargeCommandStrategy implements CommandStrategy {
    private final LoanChargesApiResource loanChargesApiResource;
    @Override
    public BatchResponse execute(BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final List<String> pathParameters = Splitter.on('/').splitToList(request.getRelativeUrl());
        Long loanId = Long.parseLong(pathParameters.get(1));
        responseBody = loanChargesApiResource.executeLoanCharge(loanId, null, request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
