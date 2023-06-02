
package org.apache.fineract.batch.command.internal;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.loanaccount.api.LoanTransactionsApiResource;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class CreateTransactionLoanCommandStrategy implements CommandStrategy {
    private final LoanTransactionsApiResource loanTransactionsApiResource;
    @Override
    public BatchResponse execute(BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final List<String> pathParameters = Splitter.on('/').splitToList(request.getRelativeUrl());
        Long loanId = Long.parseLong(pathParameters.get(1));
        Pattern commandPattern = Pattern.compile("^?command=[a-zA-Z]+");
        Matcher commandMatcher = commandPattern.matcher(pathParameters.get(2));
        if (!commandMatcher.find()) {
            response.setRequestId(request.getRequestId());
            response.setStatusCode(HttpStatus.SC_NOT_IMPLEMENTED);
            response.setBody(
                    "Resource with method " + request.getMethod() + " and relativeUrl " + request.getRelativeUrl() + " doesn't exist");
            return response;
        }
        String commandQueryParam = commandMatcher.group(0);
        String command = commandQueryParam.substring(commandQueryParam.indexOf("=") + 1);
        responseBody = loanTransactionsApiResource.executeLoanTransaction(loanId, command, request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
