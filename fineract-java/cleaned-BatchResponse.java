
package org.apache.fineract.batch.domain;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchResponse {
    private Long requestId;
    private Integer statusCode;
    private Set<Header> headers;
    private String body;
}
