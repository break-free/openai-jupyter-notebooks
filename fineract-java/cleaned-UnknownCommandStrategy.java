
package org.apache.fineract.batch.command.internal;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.batch.command.CommandStrategy;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.springframework.stereotype.Component;
@Component
public class UnknownCommandStrategy implements CommandStrategy {
    @Override
    public BatchResponse execute(BatchRequest batchRequest, @SuppressWarnings("unused") UriInfo uriInfo) {
        final BatchResponse batchResponse = new BatchResponse();
        batchResponse.setRequestId(batchRequest.getRequestId());
        batchResponse.setStatusCode(501);
        batchResponse.setBody("Resource with method " + batchRequest.getMethod() + " and relativeUrl " + batchRequest.getRelativeUrl()
                + " doesn't exist");
        return batchResponse;
    }
}
