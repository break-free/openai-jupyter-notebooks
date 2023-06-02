
package org.apache.fineract.interoperation.data;
import java.beans.Transient;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.apache.fineract.interoperation.domain.InteropActionState;
public final class InteropTransferResponseData extends InteropResponseData {
    @NotNull
    private final String transferCode;
    private String completedTimestamp;
    private InteropTransferResponseData(Long resourceId, Long officeId, Long commandId, Map<String, Object> changesOnly,
            @NotNull String transactionCode, @NotNull InteropActionState state, LocalDateTime expiration, List<ExtensionData> extensionList,
            @NotNull String transferCode, LocalDateTime completedTimestamp) {
        super(resourceId, officeId, commandId, changesOnly, transactionCode, state, expiration, extensionList);
        this.transferCode = transferCode;
        this.completedTimestamp = format(completedTimestamp);
    }
    public static InteropTransferResponseData build(Long commandId, @NotNull String transactionCode, @NotNull InteropActionState state,
            LocalDateTime expiration, List<ExtensionData> extensionList, @NotNull String transferCode, LocalDateTime completedTimestamp) {
        return new InteropTransferResponseData(null, null, commandId, null, transactionCode, state, expiration, extensionList, transferCode,
                completedTimestamp);
    }
    public static InteropTransferResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            List<ExtensionData> extensionList, @NotNull String transferCode, LocalDateTime completedTimestamp) {
        return build(null, transactionCode, state, null, extensionList, transferCode, completedTimestamp);
    }
    public static InteropTransferResponseData build(Long commandId, @NotNull String transactionCode, @NotNull InteropActionState state,
            @NotNull String transferCode) {
        return build(commandId, transactionCode, state, null, null, transferCode, null);
    }
    public static InteropTransferResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            @NotNull String transferCode) {
        return build(null, transactionCode, state, transferCode);
    }
    public String getTransferCode() {
        return transferCode;
    }
    public String getCompletedTimestamp() {
        return completedTimestamp;
    }
    @Transient
    public LocalDateTime getCompletedTimestampDate() throws ParseException {
        return parse(completedTimestamp);
    }
}
