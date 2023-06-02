
package org.apache.fineract.batch.command.internal;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.api.MutableUriInfo;
import org.apache.fineract.portfolio.loanaccount.api.LoansApiResource;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class GetLoanByIdCommandStrategy implements CommandStrategy {
    private final LoansApiResource loansApiResource;
    @Override
    public BatchResponse execute(final BatchRequest request, final UriInfo uriInfo) {
        final MutableUriInfo parameterizedUriInfo = new MutableUriInfo(uriInfo);
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final String relativeUrl = request.getRelativeUrl();
        Long loanId;
        Map<String, String> queryParameters = null;
        if (relativeUrl.indexOf('?') > 0) {
            loanId = Long.parseLong(StringUtils.substringBetween(relativeUrl, "/", "?"));
            queryParameters = getQueryParameters(relativeUrl);
            addQueryParametersToUriInfo(parameterizedUriInfo, queryParameters);
        } else {
            loanId = Long.parseLong(StringUtils.substringAfter(relativeUrl, "/"));
        }
        final boolean staffInSelectedOfficeOnly = false;
        String associations = null;
        String exclude = null;
        String fields = null;
        if (queryParameters != null && queryParameters.size() > 0) {
            if (queryParameters.containsKey("associations")) {
                associations = queryParameters.get("associations");
            }
            if (queryParameters.containsKey("exclude")) {
                exclude = queryParameters.get("exclude");
            }
            if (queryParameters.containsKey("fields")) {
                fields = queryParameters.get("fields");
            }
        }
        responseBody = loansApiResource.retrieveLoan(loanId, staffInSelectedOfficeOnly, associations, exclude, fields,
                parameterizedUriInfo);
        response.setStatusCode(HttpStatus.SC_OK);
        response.setBody(responseBody);
        return response;
    }
    private Map<String, String> getQueryParameters(final String relativeUrl) {
        final String queryParameterStr = StringUtils.substringAfter(relativeUrl, "?");
        final String[] queryParametersArray = StringUtils.split(queryParameterStr, "&");
        final Map<String, String> queryParametersMap = new HashMap<>();
        for (String parameterStr : queryParametersArray) {
            String[] keyValue = StringUtils.split(parameterStr, "=");
            queryParametersMap.put(keyValue[0], keyValue[1]);
        }
        return queryParametersMap;
    }
    private void addQueryParametersToUriInfo(final MutableUriInfo uriInfo, final Map<String, String> queryParameters) {
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            uriInfo.addAdditionalQueryParameter(entry.getKey(), entry.getValue());
        }
    }
}
