
package org.apache.fineract.batch.command.internal;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.command.CommandStrategyUtils;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.api.MutableUriInfo;
import org.apache.fineract.infrastructure.dataqueries.api.DatatablesApiResource;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class GetDatatableEntryByAppTableIdCommandStrategy implements CommandStrategy {
    private final DatatablesApiResource dataTablesApiResource;
    @Override
    public BatchResponse execute(final BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final MutableUriInfo parameterizedUriInfo = new MutableUriInfo(uriInfo);
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final String relativeUrl = request.getRelativeUrl();
        final String relativeUrlSubString = StringUtils.substringAfter(relativeUrl, "/");
        long appTableId;
        if (relativeUrl.indexOf('?') > 0) {
            appTableId = Long.parseLong(StringUtils.substringBetween(relativeUrlSubString, "/", "?"));
            Map<String, String> queryParameters = CommandStrategyUtils.getQueryParameters(relativeUrl);
            CommandStrategyUtils.addQueryParametersToUriInfo(parameterizedUriInfo, queryParameters);
        } else {
            appTableId = Long.parseLong(StringUtils.substringAfter(relativeUrlSubString, "/"));
        }
        String dataTableName = relativeUrlSubString.substring(0, relativeUrlSubString.indexOf("/"));
        responseBody = dataTablesApiResource.getDatatable(dataTableName, appTableId, null, parameterizedUriInfo);
        response.setStatusCode(HttpStatus.SC_OK);
        response.setBody(responseBody);
        return response;
    }
}
