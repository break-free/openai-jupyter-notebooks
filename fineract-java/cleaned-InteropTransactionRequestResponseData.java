
package org.apache.fineract.interoperation.data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.apache.fineract.interoperation.domain.InteropActionState;
public final class InteropTransactionRequestResponseData extends InteropResponseData {
    @NotNull
    private final String requestCode;
    private InteropTransactionRequestResponseData(Long resourceId, Long officeId, Long commandId, Map<String, Object> changesOnly,
            @NotNull String transactionCode, @NotNull InteropActionState state, LocalDateTime expiration, List<ExtensionData> extensionList,
            @NotNull String requestCode) {
        super(resourceId, officeId, commandId, changesOnly, transactionCode, state, expiration, extensionList);
        this.requestCode = requestCode;
    }
    public static InteropTransactionRequestResponseData build(Long commandId, @NotNull String transactionCode,
            @NotNull InteropActionState state, LocalDateTime expiration, List<ExtensionData> extensionList, @NotNull String requestCode) {
        return new InteropTransactionRequestResponseData(null, null, commandId, null, transactionCode, state, expiration, extensionList,
                requestCode);
    }
    public static InteropTransactionRequestResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            LocalDateTime expiration, List<ExtensionData> extensionList, @NotNull String requestCode) {
        return build(null, transactionCode, state, expiration, extensionList, requestCode);
    }
    public static InteropTransactionRequestResponseData build(Long commandId, @NotNull String transactionCode,
            @NotNull InteropActionState state, @NotNull String requestCode) {
        return build(commandId, transactionCode, state, null, null, requestCode);
    }
    public static InteropTransactionRequestResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            @NotNull String requestCode) {
        return build(null, transactionCode, state, requestCode);
    }
    public String getRequestCode() {
        return requestCode;
    }
}
