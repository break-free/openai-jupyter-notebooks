
package org.apache.fineract.batch.command.internal;
import com.google.common.base.Splitter;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.portfolio.client.api.ClientsApiResource;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ActivateClientCommandStrategy implements CommandStrategy {
    private final ClientsApiResource clientsApiResource;
    @Override
    public BatchResponse execute(final BatchRequest request, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse response = new BatchResponse();
        final String responseBody;
        response.setRequestId(request.getRequestId());
        response.setHeaders(request.getHeaders());
        final List<String> pathParameters = Splitter.on('/').splitToList(request.getRelativeUrl());
        Long clientId = Long.parseLong(pathParameters.get(1).substring(0, pathParameters.get(1).indexOf("?")));
        responseBody = clientsApiResource.activate(clientId, "activate", request.getBody());
        response.setStatusCode(200);
        response.setBody(responseBody);
        return response;
    }
}
