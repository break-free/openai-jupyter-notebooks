
package org.apache.fineract.batch.command.internal;
import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.command.CommandStrategyUtils;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.api.MutableUriInfo;
import org.apache.fineract.portfolio.loanaccount.api.LoanTransactionsApiResource;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class GetTransactionByIdCommandStrategy implements CommandStrategy {
    private final LoanTransactionsApiResource loanTransactionsApiResource;
    @Override
    public BatchResponse execute(final BatchRequest request, UriInfo uriInfo) {
        final MutableUriInfo parameterizedUriInfo = new MutableUriInfo(uriInfo);
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final String relativeUrl = request.getRelativeUrl();
        final List<String> pathParameters = Splitter.on('/').splitToList(relativeUrl);
        Long loanId = Long.parseLong(pathParameters.get(1));
        Long transactionId;
        if (relativeUrl.indexOf('?') > 0) {
            transactionId = Long.parseLong(StringUtils.substringBeforeLast(pathParameters.get(3), "?"));
        } else {
            transactionId = Long.parseLong(pathParameters.get(3));
        }
        Map<String, String> queryParameters = new HashMap<>();
        if (relativeUrl.indexOf('?') > 0) {
            queryParameters = CommandStrategyUtils.getQueryParameters(relativeUrl);
            CommandStrategyUtils.addQueryParametersToUriInfo(parameterizedUriInfo, queryParameters);
        }
        String fields = null;
        if (!queryParameters.isEmpty()) {
            if (queryParameters.containsKey("fields")) {
                fields = queryParameters.get("fields");
            }
        }
        responseBody = loanTransactionsApiResource.retrieveTransaction(loanId, transactionId, fields, uriInfo);
        response.setStatusCode(HttpStatus.SC_OK);
        response.setBody(responseBody);
        return response;
    }
}
