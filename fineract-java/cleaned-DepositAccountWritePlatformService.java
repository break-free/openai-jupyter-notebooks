
package org.apache.fineract.portfolio.savings.service;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.jobs.exception.JobExecutionException;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.staff.domain.Staff;
import org.apache.fineract.portfolio.savings.DepositAccountType;
import org.apache.fineract.portfolio.savings.data.SavingsAccountTransactionDTO;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountTransaction;
public interface DepositAccountWritePlatformService {
    CommandProcessingResult activateFDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult activateRDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult updateDepositAmountForRDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult depositToFDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult depositToRDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult withdrawal(Long savingsId, JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult calculateInterest(Long savingsId, DepositAccountType depositAccountType);
    CommandProcessingResult postInterest(Long savingsId, DepositAccountType depositAccountType);
    CommandProcessingResult undoFDTransaction(Long savingsId, Long transactionId, boolean allowAccountTransferModification);
    CommandProcessingResult undoRDTransaction(Long savingsId, Long transactionId, boolean allowAccountTransferModification);
    CommandProcessingResult adjustFDTransaction(Long savingsId, Long transactionId, JsonCommand command);
    CommandProcessingResult adjustRDTransaction(Long savingsId, Long transactionId, JsonCommand command);
    CommandProcessingResult closeFDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult closeRDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult prematureCloseFDAccount(Long savingsId, JsonCommand command);
    CommandProcessingResult prematureCloseRDAccount(Long savingsId, JsonCommand command);
    SavingsAccountTransaction initiateSavingsTransfer(Long accountId, LocalDate transferDate, DepositAccountType depositAccountType);
    SavingsAccountTransaction withdrawSavingsTransfer(Long accountId, LocalDate transferDate, DepositAccountType depositAccountType);
    void rejectSavingsTransfer(Long accountId, DepositAccountType depositAccountType);
    SavingsAccountTransaction acceptSavingsTransfer(Long accountId, LocalDate transferDate, Office acceptedInOffice, Staff staff,
            DepositAccountType depositAccountType);
    CommandProcessingResult addSavingsAccountCharge(JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult updateSavingsAccountCharge(JsonCommand command, DepositAccountType depositAccountType);
    CommandProcessingResult deleteSavingsAccountCharge(Long savingsAccountId, Long savingsAccountChargeId, JsonCommand command,
            DepositAccountType depositAccountType);
    CommandProcessingResult waiveCharge(Long savingsAccountId, Long savingsAccountChargeId, DepositAccountType depositAccountType);
    CommandProcessingResult payCharge(Long savingsAccountId, Long savingsAccountChargeId, JsonCommand command,
            DepositAccountType depositAccountType);
    void applyChargeDue(Long savingsAccountChargeId, Long accountId, DepositAccountType depositAccountType);
    void updateMaturityDetails(Long depositAccountId, DepositAccountType depositAccountType);
    void transferInterestToSavings() throws JobExecutionException;
    SavingsAccountTransaction mandatorySavingsAccountDeposit(SavingsAccountTransactionDTO accountTransactionDTO);
}
