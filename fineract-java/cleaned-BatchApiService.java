
package org.apache.fineract.batch.service;
import java.util.List;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
public interface BatchApiService {
    List<BatchResponse> handleBatchRequestsWithoutEnclosingTransaction(List<BatchRequest> requestList, UriInfo uriInfo);
    List<BatchResponse> handleBatchRequestsWithEnclosingTransaction(List<BatchRequest> requestList, UriInfo uriInfo);
}
