
package org.apache.fineract.batch.command;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
public interface CommandStrategy {
    BatchResponse execute(BatchRequest batchRequest, UriInfo uriInfo);
}
