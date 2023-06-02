
package org.apache.fineract.interoperation.data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.apache.fineract.interoperation.domain.InteropActionState;
public final class InteropQuoteResponseData extends InteropResponseData {
    @NotNull
    private final String quoteCode;
    private MoneyData fspFee;
    private MoneyData fspCommission;
    private InteropQuoteResponseData(Long resourceId, Long officeId, Long commandId, Map<String, Object> changesOnly,
            @NotNull String transactionCode, @NotNull InteropActionState state, LocalDateTime expiration, List<ExtensionData> extensionList,
            @NotNull String quoteCode, MoneyData fspFee, MoneyData fspCommission) {
        super(resourceId, officeId, commandId, changesOnly, transactionCode, state, expiration, extensionList);
        this.quoteCode = quoteCode;
        this.fspFee = fspFee;
        this.fspCommission = fspCommission;
    }
    public static InteropQuoteResponseData build(Long commandId, @NotNull String transactionCode, @NotNull InteropActionState state,
            LocalDateTime expiration, List<ExtensionData> extensionList, @NotNull String quoteCode, MoneyData fspFee,
            MoneyData fspCommission) {
        return new InteropQuoteResponseData(null, null, commandId, null, transactionCode, state, expiration, extensionList, quoteCode,
                fspFee, fspCommission);
    }
    public static InteropQuoteResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            LocalDateTime expiration, List<ExtensionData> extensionList, @NotNull String quoteCode, MoneyData fspFee,
            MoneyData fspCommission) {
        return build(null, transactionCode, state, expiration, extensionList, quoteCode, fspFee, fspCommission);
    }
    public static InteropQuoteResponseData build(Long commandId, @NotNull String transactionCode, @NotNull InteropActionState state,
            @NotNull String quoteCode) {
        return build(commandId, transactionCode, state, null, null, quoteCode, null, null);
    }
    public static InteropQuoteResponseData build(@NotNull String transactionCode, @NotNull InteropActionState state,
            @NotNull String quoteCode) {
        return build(null, transactionCode, state, quoteCode);
    }
    public String getQuoteCode() {
        return quoteCode;
    }
    public MoneyData getFspFee() {
        return fspFee;
    }
    public void setFspFee(MoneyData fspFee) {
        this.fspFee = fspFee;
    }
    public MoneyData getFspCommission() {
        return fspCommission;
    }
    public void setFspCommission(MoneyData fspCommission) {
        this.fspCommission = fspCommission;
    }
}
