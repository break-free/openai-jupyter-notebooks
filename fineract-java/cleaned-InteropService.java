
package org.apache.fineract.interoperation.service;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.interoperation.data.InteropAccountData;
import org.apache.fineract.interoperation.data.InteropIdentifierAccountResponseData;
import org.apache.fineract.interoperation.data.InteropIdentifiersResponseData;
import org.apache.fineract.interoperation.data.InteropKycResponseData;
import org.apache.fineract.interoperation.data.InteropQuoteResponseData;
import org.apache.fineract.interoperation.data.InteropTransactionRequestResponseData;
import org.apache.fineract.interoperation.data.InteropTransactionsData;
import org.apache.fineract.interoperation.data.InteropTransferResponseData;
import org.apache.fineract.interoperation.domain.InteropIdentifierType;
public interface InteropService {
    @NotNull
    InteropIdentifiersResponseData getAccountIdentifiers(@NotNull String accountId);
    @NotNull
    InteropAccountData getAccountDetails(@NotNull String accountId);
    @NotNull
    InteropTransactionsData getAccountTransactions(@NotNull String accountId, boolean debit, boolean credit, LocalDateTime transactionsFrom,
            LocalDateTime transactionsTo);
    @NotNull
    InteropIdentifierAccountResponseData getAccountByIdentifier(@NotNull InteropIdentifierType idType, @NotNull String idValue,
            String subIdOrType);
    @NotNull
    InteropIdentifierAccountResponseData registerAccountIdentifier(@NotNull InteropIdentifierType idType, @NotNull String idValue,
            String subIdOrType, @NotNull JsonCommand command);
    @NotNull
    InteropIdentifierAccountResponseData deleteAccountIdentifier(@NotNull InteropIdentifierType idType, @NotNull String idValue,
            String subIdOrType);
    InteropTransactionRequestResponseData getTransactionRequest(@NotNull String transactionCode, @NotNull String requestCode);
    @NotNull
    InteropTransactionRequestResponseData createTransactionRequest(@NotNull JsonCommand command);
    InteropQuoteResponseData getQuote(@NotNull String transactionCode, @NotNull String quoteCode);
    @NotNull
    InteropQuoteResponseData createQuote(@NotNull JsonCommand command);
    InteropTransferResponseData getTransfer(@NotNull String transactionCode, @NotNull String transferCode);
    @NotNull
    InteropTransferResponseData prepareTransfer(@NotNull JsonCommand command);
    @NotNull
    InteropTransferResponseData commitTransfer(@NotNull JsonCommand command);
    @NotNull
    InteropTransferResponseData releaseTransfer(@NotNull JsonCommand command);
    @NotNull
    InteropKycResponseData getKyc(@NotNull String accountId);
    @NotNull
    String disburseLoan(@NotNull String accountId, String apiRequestBodyAsJson);
}
