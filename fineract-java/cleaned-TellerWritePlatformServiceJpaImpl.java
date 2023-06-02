
package org.apache.fineract.organisation.teller.service;
import java.util.Map;
import java.util.Set;
import javax.persistence.PersistenceException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.fineract.accounting.common.AccountingConstants.FinancialActivity;
import org.apache.fineract.accounting.financialactivityaccount.domain.FinancialActivityAccount;
import org.apache.fineract.accounting.financialactivityaccount.domain.FinancialActivityAccountRepositoryWrapper;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.accounting.journalentry.domain.JournalEntry;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryRepository;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryType;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.security.exception.NoAuthorizationException;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.apache.fineract.organisation.staff.domain.Staff;
import org.apache.fineract.organisation.staff.domain.StaffRepository;
import org.apache.fineract.organisation.staff.exception.StaffNotFoundException;
import org.apache.fineract.organisation.teller.data.CashierTransactionDataValidator;
import org.apache.fineract.organisation.teller.domain.Cashier;
import org.apache.fineract.organisation.teller.domain.CashierRepository;
import org.apache.fineract.organisation.teller.domain.CashierTransaction;
import org.apache.fineract.organisation.teller.domain.CashierTransactionRepository;
import org.apache.fineract.organisation.teller.domain.CashierTxnType;
import org.apache.fineract.organisation.teller.domain.Teller;
import org.apache.fineract.organisation.teller.domain.TellerRepositoryWrapper;
import org.apache.fineract.organisation.teller.exception.CashierExistForTellerException;
import org.apache.fineract.organisation.teller.exception.CashierNotFoundException;
import org.apache.fineract.organisation.teller.serialization.TellerCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.client.domain.ClientTransaction;
import org.apache.fineract.useradministration.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TellerWritePlatformServiceJpaImpl implements TellerWritePlatformService {
    private static final Logger LOG = LoggerFactory.getLogger(TellerWritePlatformServiceJpaImpl.class);
    private final PlatformSecurityContext context;
    private final TellerCommandFromApiJsonDeserializer fromApiJsonDeserializer;
    private final TellerRepositoryWrapper tellerRepositoryWrapper;
    private final OfficeRepositoryWrapper officeRepositoryWrapper;
    private final StaffRepository staffRepository;
    private final CashierRepository cashierRepository;
    private final CashierTransactionRepository cashierTxnRepository;
    private final JournalEntryRepository glJournalEntryRepository;
    private final FinancialActivityAccountRepositoryWrapper financialActivityAccountRepositoryWrapper;
    private final CashierTransactionDataValidator cashierTransactionDataValidator;
    @Autowired
    public TellerWritePlatformServiceJpaImpl(final PlatformSecurityContext context,
            final TellerCommandFromApiJsonDeserializer fromApiJsonDeserializer, final TellerRepositoryWrapper tellerRepositoryWrapper,
            final OfficeRepositoryWrapper officeRepositoryWrapper, final StaffRepository staffRepository,
            CashierRepository cashierRepository, CashierTransactionRepository cashierTxnRepository,
            JournalEntryRepository glJournalEntryRepository,
            FinancialActivityAccountRepositoryWrapper financialActivityAccountRepositoryWrapper,
            final CashierTransactionDataValidator cashierTransactionDataValidator) {
        this.context = context;
        this.fromApiJsonDeserializer = fromApiJsonDeserializer;
        this.tellerRepositoryWrapper = tellerRepositoryWrapper;
        this.officeRepositoryWrapper = officeRepositoryWrapper;
        this.staffRepository = staffRepository;
        this.cashierRepository = cashierRepository;
        this.cashierTxnRepository = cashierTxnRepository;
        this.glJournalEntryRepository = glJournalEntryRepository;
        this.financialActivityAccountRepositoryWrapper = financialActivityAccountRepositoryWrapper;
        this.cashierTransactionDataValidator = cashierTransactionDataValidator;
    }
    @Override
    @Transactional
    public CommandProcessingResult createTeller(JsonCommand command) {
        try {
            this.context.authenticatedUser();
            final Long officeId = command.longValueOfParameterNamed("officeId");
            this.fromApiJsonDeserializer.validateForCreateAndUpdateTeller(command.json());
            final Office tellerOffice = this.officeRepositoryWrapper.findOneWithNotFoundDetection(officeId);
            final Teller teller = Teller.fromJson(tellerOffice, command);
            this.tellerRepositoryWrapper.saveAndFlush(teller);
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(teller.getId()) 
                    .withOfficeId(teller.getOffice().getId()) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    @Override
    @Transactional
    public CommandProcessingResult modifyTeller(Long tellerId, JsonCommand command) {
        try {
            final Long officeId = command.longValueOfParameterNamed("officeId");
            final Office tellerOffice = this.officeRepositoryWrapper.findOneWithNotFoundDetection(officeId);
            final AppUser currentUser = this.context.authenticatedUser();
            this.fromApiJsonDeserializer.validateForCreateAndUpdateTeller(command.json());
            final Teller teller = validateUserPriviledgeOnTellerAndRetrieve(currentUser, tellerId);
            final Map<String, Object> changes = teller.update(tellerOffice, command);
            if (!changes.isEmpty()) {
                this.tellerRepositoryWrapper.saveAndFlush(teller);
            }
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(teller.getId()) 
                    .withOfficeId(teller.officeId()) 
                    .with(changes) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    private Teller validateUserPriviledgeOnTellerAndRetrieve(final AppUser currentUser, final Long tellerId) {
        final Long userOfficeId = currentUser.getOffice().getId();
        final Office userOffice = this.officeRepositoryWrapper.findOfficeHierarchy(userOfficeId);
        final Teller tellerToReturn = this.tellerRepositoryWrapper.findOneWithNotFoundDetection(tellerId);
        final Long tellerOfficeId = tellerToReturn.officeId();
        if (userOffice.doesNotHaveAnOfficeInHierarchyWithId(tellerOfficeId)) {
            throw new NoAuthorizationException("User does not have sufficient priviledges to act on the provided office.");
        }
        return tellerToReturn;
    }
    @Override
    @Transactional
    public CommandProcessingResult deleteTeller(Long tellerId) {
        Teller teller = tellerRepositoryWrapper.findOneWithNotFoundDetection(tellerId);
        Set<Cashier> isTellerIdPresentInCashier = teller.getCashiers();
        for (final Cashier tellerIdInCashier : isTellerIdPresentInCashier) {
            if (tellerIdInCashier.getTeller().getId().toString().equalsIgnoreCase(tellerId.toString())) {
                throw new CashierExistForTellerException(tellerId);
            }
        }
        tellerRepositoryWrapper.delete(teller);
        return new CommandProcessingResultBuilder() 
                .withEntityId(teller.getId()) 
                .build();
    }
    private void handleTellerDataIntegrityIssues(final JsonCommand command, final Throwable realCause, final Exception dve) {
        if (realCause.getMessage().contains("m_tellers_name_unq")) {
            final String name = command.stringValueOfParameterNamed("name");
            throw new PlatformDataIntegrityException("error.msg.teller.duplicate.name", "Teller with name `" + name + "` already exists",
                    "name", name);
        }
        LOG.error("Error occured.", dve);
        throw new PlatformDataIntegrityException("error.msg.teller.unknown.data.integrity.issue",
                "Unknown data integrity issue with resource.");
    }
    @Override
    public CommandProcessingResult allocateCashierToTeller(final Long tellerId, JsonCommand command) {
        try {
            this.context.authenticatedUser();
            Long hourStartTime;
            Long minStartTime;
            Long hourEndTime;
            Long minEndTime;
            String startTime = " ";
            String endTime = " ";
            final Teller teller = this.tellerRepositoryWrapper.findOneWithNotFoundDetection(tellerId);
            final Office tellerOffice = teller.getOffice();
            final Long staffId = command.longValueOfParameterNamed("staffId");
            this.fromApiJsonDeserializer.validateForAllocateCashier(command.json());
            final Staff staff = this.staffRepository.findById(staffId).orElseThrow(() -> new StaffNotFoundException(staffId));
            final Boolean isFullDay = command.booleanObjectValueOfParameterNamed("isFullDay");
            if (!isFullDay) {
                hourStartTime = command.longValueOfParameterNamed("hourStartTime");
                minStartTime = command.longValueOfParameterNamed("minStartTime");
                if (minStartTime == 0) {
                    startTime = hourStartTime.toString() + ":" + minStartTime.toString() + "0";
                } else {
                    startTime = hourStartTime.toString() + ":" + minStartTime.toString();
                }
                hourEndTime = command.longValueOfParameterNamed("hourEndTime");
                minEndTime = command.longValueOfParameterNamed("minEndTime");
                if (minEndTime == 0) {
                    endTime = hourEndTime.toString() + ":" + minEndTime.toString() + "0";
                } else {
                    endTime = hourEndTime.toString() + ":" + minEndTime.toString();
                }
            }
            final Cashier cashier = Cashier.fromJson(tellerOffice, teller, staff, startTime, endTime, command);
            this.cashierTransactionDataValidator.validateCashierAllowedDateAndTime(cashier, teller);
            this.cashierRepository.save(cashier);
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(teller.getId()) 
                    .withSubEntityId(cashier.getId()) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    @Override
    @Transactional
    public CommandProcessingResult updateCashierAllocation(Long tellerId, Long cashierId, JsonCommand command) {
        try {
            final AppUser currentUser = this.context.authenticatedUser();
            this.fromApiJsonDeserializer.validateForAllocateCashier(command.json());
            final Long staffId = command.longValueOfParameterNamed("staffId");
            final Staff staff = this.staffRepository.findById(staffId).orElseThrow(() -> new StaffNotFoundException(staffId));
            final Cashier cashier = validateUserPriviledgeOnCashierAndRetrieve(currentUser, tellerId, cashierId);
            cashier.setStaff(staff);
            final Map<String, Object> changes = cashier.update(command);
            if (!changes.isEmpty()) {
                this.cashierRepository.saveAndFlush(cashier);
            }
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(cashier.getTeller().getId()) 
                    .withSubEntityId(cashier.getId()) 
                    .with(changes) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
    private Cashier validateUserPriviledgeOnCashierAndRetrieve(final AppUser currentUser, final Long tellerId, final Long cashierId) {
        validateUserPriviledgeOnTellerAndRetrieve(currentUser, tellerId);
        return this.cashierRepository.findById(cashierId).orElse(null);
    }
    @Override
    @Transactional
    public CommandProcessingResult deleteCashierAllocation(Long tellerId, Long cashierId, JsonCommand command) {
        try {
            final AppUser currentUser = this.context.authenticatedUser();
            final Cashier cashier = validateUserPriviledgeOnCashierAndRetrieve(currentUser, tellerId, cashierId);
            this.cashierRepository.delete(cashier);
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
        return new CommandProcessingResultBuilder() 
                .withEntityId(cashierId) 
                .build();
    }
    @Override
    public CommandProcessingResult allocateCashToCashier(final Long cashierId, JsonCommand command) {
        return doTransactionForCashier(cashierId, CashierTxnType.ALLOCATE, command); 
    }
    @Override
    public CommandProcessingResult settleCashFromCashier(final Long cashierId, JsonCommand command) {
        this.cashierTransactionDataValidator.validateSettleCashAndCashOutTransactions(cashierId, command);
        return doTransactionForCashier(cashierId, CashierTxnType.SETTLE, command); 
    }
    private CommandProcessingResult doTransactionForCashier(final Long cashierId, final CashierTxnType txnType, JsonCommand command) {
        try {
            final AppUser currentUser = this.context.authenticatedUser();
            final Cashier cashier = this.cashierRepository.findById(cashierId).orElseThrow(() -> new CashierNotFoundException(cashierId));
            this.fromApiJsonDeserializer.validateForCashTxnForCashier(command.json());
            final String entityType = command.stringValueOfParameterNamed("entityType");
            final Long entityId = command.longValueOfParameterNamed("entityId");
            if (entityType != null) {
                if (entityType.equals("loan account")) {
                } else if (entityType.equals("savings account")) {
                }
                if (entityType.equals("client")) {
                } else {
                }
            }
            final CashierTransaction cashierTxn = CashierTransaction.fromJson(cashier, command);
            cashierTxn.setTxnType(txnType.getId());
            this.cashierTxnRepository.save(cashierTxn);
            FinancialActivityAccount mainVaultFinancialActivityAccount = this.financialActivityAccountRepositoryWrapper
                    .findByFinancialActivityTypeWithNotFoundDetection(FinancialActivity.CASH_AT_MAINVAULT.getValue());
            FinancialActivityAccount tellerCashFinancialActivityAccount = this.financialActivityAccountRepositoryWrapper
                    .findByFinancialActivityTypeWithNotFoundDetection(FinancialActivity.CASH_AT_TELLER.getValue());
            GLAccount creditAccount = null;
            GLAccount debitAccount = null;
            if (txnType.equals(CashierTxnType.ALLOCATE)) {
                debitAccount = tellerCashFinancialActivityAccount.getGlAccount();
                creditAccount = mainVaultFinancialActivityAccount.getGlAccount();
            } else if (txnType.equals(CashierTxnType.SETTLE)) {
                debitAccount = mainVaultFinancialActivityAccount.getGlAccount();
                creditAccount = tellerCashFinancialActivityAccount.getGlAccount();
            }
            final Office cashierOffice = cashier.getTeller().getOffice();
            final Long time = System.currentTimeMillis();
            final String uniqueVal = String.valueOf(time) + currentUser.getId() + cashierOffice.getId();
            final String transactionId = Long.toHexString(Long.parseLong(uniqueVal));
            ClientTransaction clientTransaction = null;
            final Long shareTransactionId = null;
            final JournalEntry debitJournalEntry = JournalEntry.createNew(cashierOffice, null, 
                    debitAccount, cashierTxn.getCurrencyCode(),
                    transactionId, false, 
                    cashierTxn.getTxnDate(), JournalEntryType.DEBIT, cashierTxn.getTxnAmount(), cashierTxn.getTxnNote(), 
                    null, null, null, 
                    null, null, clientTransaction, shareTransactionId); 
            final JournalEntry creditJournalEntry = JournalEntry.createNew(cashierOffice, null, 
                    creditAccount, cashierTxn.getCurrencyCode(),
                    transactionId, false, 
                    cashierTxn.getTxnDate(), JournalEntryType.CREDIT, cashierTxn.getTxnAmount(), cashierTxn.getTxnNote(), 
                    null, null, null, 
                    null, null, clientTransaction, shareTransactionId); 
            this.glJournalEntryRepository.saveAndFlush(debitJournalEntry);
            this.glJournalEntryRepository.saveAndFlush(creditJournalEntry);
            return new CommandProcessingResultBuilder() 
                    .withCommandId(command.commandId()) 
                    .withEntityId(cashier.getId()) 
                    .withSubEntityId(cashierTxn.getId()) 
                    .build();
        } catch (final JpaSystemException | DataIntegrityViolationException dve) {
            handleTellerDataIntegrityIssues(command, dve.getMostSpecificCause(), dve);
            return CommandProcessingResult.empty();
        } catch (final PersistenceException dve) {
            Throwable throwable = ExceptionUtils.getRootCause(dve.getCause());
            handleTellerDataIntegrityIssues(command, throwable, dve);
            return CommandProcessingResult.empty();
        }
    }
}
