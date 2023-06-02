
package org.apache.fineract.batch.command.internal;
import com.google.common.base.Splitter;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.rescheduleloan.api.RescheduleLoansApiResource;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ApproveLoanRescheduleCommandStrategy implements CommandStrategy {
    private final RescheduleLoansApiResource rescheduleLoansApiResource;
    @Override
    public BatchResponse execute(BatchRequest request, UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final List<String> pathParameters = Splitter.on('/').splitToList(request.getRelativeUrl());
        Long scheduleId = Long.parseLong(pathParameters.get(1).substring(0, pathParameters.get(1).indexOf("?")));
        responseBody = rescheduleLoansApiResource.updateLoanRescheduleRequest(scheduleId, "approve", request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
