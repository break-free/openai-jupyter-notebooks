
package org.apache.fineract.interoperation.data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class InteropRefundData {
    @Size(max = 36)
    @NotNull
    private String originalTransactionId; 
    @Size(max = 128)
    private String refundReason; 
}
