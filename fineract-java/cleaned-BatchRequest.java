
package org.apache.fineract.batch.domain;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchRequest {
    private Long requestId;
    private String relativeUrl;
    private String method;
    private Set<Header> headers;
    private Long reference;
    private String body;
}
