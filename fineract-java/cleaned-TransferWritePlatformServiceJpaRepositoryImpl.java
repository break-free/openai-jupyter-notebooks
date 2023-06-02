
package org.apache.fineract.portfolio.transfer.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.core.exception.GeneralPlatformDomainRuleException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.apache.fineract.organisation.staff.domain.Staff;
import org.apache.fineract.organisation.staff.domain.StaffRepositoryWrapper;
import org.apache.fineract.portfolio.calendar.domain.Calendar;
import org.apache.fineract.portfolio.calendar.domain.CalendarEntityType;
import org.apache.fineract.portfolio.calendar.domain.CalendarInstance;
import org.apache.fineract.portfolio.calendar.domain.CalendarInstanceRepository;
import org.apache.fineract.portfolio.calendar.domain.CalendarType;
import org.apache.fineract.portfolio.calendar.service.CalendarUtils;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.client.domain.ClientStatus;
import org.apache.fineract.portfolio.client.domain.ClientTransferDetails;
import org.apache.fineract.portfolio.client.domain.ClientTransferDetailsRepositoryWrapper;
import org.apache.fineract.portfolio.client.exception.ClientHasBeenClosedException;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.group.domain.GroupRepositoryWrapper;
import org.apache.fineract.portfolio.group.exception.ClientNotInGroupException;
import org.apache.fineract.portfolio.group.exception.GroupNotActiveException;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.service.LoanWritePlatformService;
import org.apache.fineract.portfolio.note.service.NoteWritePlatformService;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.apache.fineract.portfolio.savings.domain.SavingsAccountRepositoryWrapper;
import org.apache.fineract.portfolio.savings.service.SavingsAccountWritePlatformService;
import org.apache.fineract.portfolio.transfer.api.TransferApiConstants;
import org.apache.fineract.portfolio.transfer.data.TransfersDataValidator;
import org.apache.fineract.portfolio.transfer.exception.ClientNotAwaitingTransferApprovalException;
import org.apache.fineract.portfolio.transfer.exception.ClientNotAwaitingTransferApprovalOrOnHoldException;
import org.apache.fineract.portfolio.transfer.exception.TransferNotSupportedException;
import org.apache.fineract.portfolio.transfer.exception.TransferNotSupportedException.TransferNotSupportedReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TransferWritePlatformServiceJpaRepositoryImpl implements TransferWritePlatformService {
    private final ClientRepositoryWrapper clientRepositoryWrapper;
    private final OfficeRepositoryWrapper officeRepository;
    private final CalendarInstanceRepository calendarInstanceRepository;
    private final GroupRepositoryWrapper groupRepository;
    private final LoanWritePlatformService loanWritePlatformService;
    private final SavingsAccountWritePlatformService savingsAccountWritePlatformService;
    private final LoanRepositoryWrapper loanRepositoryWrapper;
    private final SavingsAccountRepositoryWrapper savingsAccountRepositoryWrapper;
    private final TransfersDataValidator transfersDataValidator;
    private final NoteWritePlatformService noteWritePlatformService;
    private final StaffRepositoryWrapper staffRepositoryWrapper;
    private final ClientTransferDetailsRepositoryWrapper clientTransferDetailsRepositoryWrapper;
    private final PlatformSecurityContext context;
    @Autowired
    public TransferWritePlatformServiceJpaRepositoryImpl(final ClientRepositoryWrapper clientRepositoryWrapper,
            final OfficeRepositoryWrapper officeRepository, final CalendarInstanceRepository calendarInstanceRepository,
            final LoanWritePlatformService loanWritePlatformService, final GroupRepositoryWrapper groupRepository,
            final LoanRepositoryWrapper loanRepositoryWrapper, final TransfersDataValidator transfersDataValidator,
            final NoteWritePlatformService noteWritePlatformService, final StaffRepositoryWrapper staffRepositoryWrapper,
            final SavingsAccountRepositoryWrapper savingsAccountRepositoryWrapper,
            final SavingsAccountWritePlatformService savingsAccountWritePlatformService,
            final ClientTransferDetailsRepositoryWrapper clientTransferDetailsRepositoryWrapper, final PlatformSecurityContext context) {
        this.clientRepositoryWrapper = clientRepositoryWrapper;
        this.officeRepository = officeRepository;
        this.calendarInstanceRepository = calendarInstanceRepository;
        this.loanWritePlatformService = loanWritePlatformService;
        this.groupRepository = groupRepository;
        this.loanRepositoryWrapper = loanRepositoryWrapper;
        this.transfersDataValidator = transfersDataValidator;
        this.noteWritePlatformService = noteWritePlatformService;
        this.staffRepositoryWrapper = staffRepositoryWrapper;
        this.savingsAccountRepositoryWrapper = savingsAccountRepositoryWrapper;
        this.savingsAccountWritePlatformService = savingsAccountWritePlatformService;
        this.clientTransferDetailsRepositoryWrapper = clientTransferDetailsRepositoryWrapper;
        this.context = context;
    }
    @Override
    @Transactional
    public CommandProcessingResult transferClientsBetweenGroups(final Long sourceGroupId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForClientsTransferBetweenGroups(jsonCommand.json());
        final Group sourceGroup = this.groupRepository.findOneWithNotFoundDetection(sourceGroupId);
        final Long destinationGroupId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.destinationGroupIdParamName);
        final Group destinationGroup = this.groupRepository.findOneWithNotFoundDetection(destinationGroupId);
        final Long staffId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.newStaffIdParamName);
        final Boolean inheritDestinationGroupLoanOfficer = jsonCommand
                .booleanObjectValueOfParameterNamed(TransferApiConstants.inheritDestinationGroupLoanOfficer);
        Staff staff = null;
        final Office sourceOffice = sourceGroup.getOffice();
        if (staffId != null) {
            staff = this.staffRepositoryWrapper.findByOfficeHierarchyWithNotFoundDetection(staffId, sourceOffice.getHierarchy());
        }
        final List<Client> clients = assembleListOfClients(jsonCommand);
        if (sourceGroupId.equals(destinationGroupId)) {
            throw new TransferNotSupportedException(TransferNotSupportedReason.SOURCE_AND_DESTINATION_GROUP_CANNOT_BE_SAME, sourceGroupId,
                    destinationGroupId);
        }
        if (!sourceOffice.getId().equals(destinationGroup.getOffice().getId())) {
            throw new TransferNotSupportedException(TransferNotSupportedReason.BULK_CLIENT_TRANSFER_ACROSS_BRANCHES, sourceGroupId,
                    destinationGroupId);
        }
        for (final Client client : clients) {
            transferClientBetweenGroups(sourceGroup, client, destinationGroup, inheritDestinationGroupLoanOfficer, staff);
        }
        return new CommandProcessingResultBuilder() 
                .withEntityId(sourceGroupId) 
                .build();
    }
    @Transactional
    public void transferClientBetweenGroups(final Group sourceGroup, final Client client, final Group destinationGroup,
            final Boolean inheritDestinationGroupLoanOfficer, final Staff newLoanOfficer) {
        if (!sourceGroup.hasClientAsMember(client)) {
            throw new ClientNotInGroupException(client.getId(), sourceGroup.getId());
        }
        if (client.isNotActive()) {
            throw new ClientHasBeenClosedException(client.getId());
        }
        final CalendarInstance sourceGroupCalendarInstance = this.calendarInstanceRepository.findByEntityIdAndEntityTypeIdAndCalendarTypeId(
                sourceGroup.getId(), CalendarEntityType.GROUPS.getValue(), CalendarType.COLLECTION.getValue());
        final List<CalendarInstance> activeLoanCalendarInstances = this.calendarInstanceRepository
                .findCalendarInstancesForActiveLoansByGroupIdAndClientId(sourceGroup.getId(), client.getId());
        if (sourceGroupCalendarInstance != null && !activeLoanCalendarInstances.isEmpty()) {
            final CalendarInstance destinationGroupCalendarInstance = this.calendarInstanceRepository
                    .findByEntityIdAndEntityTypeIdAndCalendarTypeId(destinationGroup.getId(), CalendarEntityType.GROUPS.getValue(),
                            CalendarType.COLLECTION.getValue());
            if (destinationGroupCalendarInstance == null) {
                throw new TransferNotSupportedException(TransferNotSupportedReason.DESTINATION_GROUP_HAS_NO_MEETING,
                        destinationGroup.getId());
            }
            final Calendar sourceGroupCalendar = sourceGroupCalendarInstance.getCalendar();
            final Calendar destinationGroupCalendar = destinationGroupCalendarInstance.getCalendar();
            if (!(CalendarUtils.isFrequencySame(sourceGroupCalendar.getRecurrence(), destinationGroupCalendar.getRecurrence())
                    && CalendarUtils.isIntervalSame(sourceGroupCalendar.getRecurrence(), destinationGroupCalendar.getRecurrence()))) {
                throw new TransferNotSupportedException(TransferNotSupportedReason.DESTINATION_GROUP_MEETING_FREQUENCY_MISMATCH,
                        sourceGroup.getId(), destinationGroup.getId());
            }
            for (final CalendarInstance calendarInstance : activeLoanCalendarInstances) {
                calendarInstance.updateCalendar(destinationGroupCalendar);
                this.calendarInstanceRepository.saveAndFlush(calendarInstance);
            }
            this.loanWritePlatformService.applyMeetingDateChanges(destinationGroupCalendar, activeLoanCalendarInstances);
        }
        final Staff destinationGroupLoanOfficer = destinationGroup.getStaff();
        if (sourceGroup.getId().equals(destinationGroup.getId()) && newLoanOfficer != null) {
            client.updateStaff(newLoanOfficer);
        } 
        else if (destinationGroupLoanOfficer != null) {
            client.updateStaff(destinationGroupLoanOfficer);
        }
        client.getGroups().add(destinationGroup);
        this.clientRepositoryWrapper.saveAndFlush(client);
        final List<Loan> allClientJLGLoans = this.loanRepositoryWrapper.findByClientIdAndGroupId(client.getId(), sourceGroup.getId());
        for (final Loan loan : allClientJLGLoans) {
            if (loan.status().isActiveOrAwaitingApprovalOrDisbursal()) {
                loan.updateGroup(destinationGroup);
                if (inheritDestinationGroupLoanOfficer != null && inheritDestinationGroupLoanOfficer == true
                        && destinationGroupLoanOfficer != null) {
                    loan.reassignLoanOfficer(destinationGroupLoanOfficer, DateUtils.getBusinessLocalDate());
                } else if (newLoanOfficer != null) {
                    loan.reassignLoanOfficer(newLoanOfficer, DateUtils.getBusinessLocalDate());
                }
                this.loanRepositoryWrapper.saveAndFlush(loan);
            }
        }
        if (!sourceGroup.getId().equals(destinationGroup.getId())) {
            client.getGroups().remove(sourceGroup);
        }
    }
    @Transactional
    @Override
    public CommandProcessingResult proposeAndAcceptClientTransfer(final Long clientId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForProposeAndAcceptClientTransfer(jsonCommand.json());
        final Long destinationOfficeId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.destinationOfficeIdParamName);
        final Office office = this.officeRepository.findOneWithNotFoundDetection(destinationOfficeId);
        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId, true);
        handleClientTransferLifecycleEvent(client, office, TransferEventType.PROPOSAL, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        handleClientTransferLifecycleEvent(client, client.getTransferToOffice(), TransferEventType.ACCEPTANCE, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        return new CommandProcessingResultBuilder() 
                .withClientId(clientId) 
                .withEntityId(clientId) 
                .build();
    }
    @Transactional
    @Override
    public CommandProcessingResult proposeClientTransfer(final Long clientId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForProposeClientTransfer(jsonCommand.json());
        final Long destinationOfficeId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.destinationOfficeIdParamName);
        final Office office = this.officeRepository.findOneWithNotFoundDetection(destinationOfficeId);
        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
        if (client.getOffice().getId().equals(destinationOfficeId)) {
            throw new GeneralPlatformDomainRuleException(TransferApiConstants.transferClientToSameOfficeException,
                    TransferApiConstants.transferClientToSameOfficeExceptionMessage, office.getName());
        }
        handleClientTransferLifecycleEvent(client, office, TransferEventType.PROPOSAL, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        return new CommandProcessingResultBuilder() 
                .withClientId(clientId) 
                .withEntityId(clientId) 
                .build();
    }
    @Transactional
    @Override
    public CommandProcessingResult acceptClientTransfer(final Long clientId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForAcceptClientTransfer(jsonCommand.json());
        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId, true);
        validateClientAwaitingTransferAcceptance(client);
        handleClientTransferLifecycleEvent(client, client.getTransferToOffice(), TransferEventType.ACCEPTANCE, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        return new CommandProcessingResultBuilder() 
                .withClientId(clientId) 
                .withEntityId(clientId) 
                .build();
    }
    @Override
    @Transactional
    public CommandProcessingResult withdrawClientTransfer(final Long clientId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForWithdrawClientTransfer(jsonCommand.json());
        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
        validateClientAwaitingTransferAcceptanceOnHold(client);
        handleClientTransferLifecycleEvent(client, client.getOffice(), TransferEventType.WITHDRAWAL, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        return new CommandProcessingResultBuilder() 
                .withClientId(clientId) 
                .withEntityId(clientId) 
                .build();
    }
    @Override
    @Transactional
    public CommandProcessingResult rejectClientTransfer(final Long clientId, final JsonCommand jsonCommand) {
        this.transfersDataValidator.validateForRejectClientTransfer(jsonCommand.json());
        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(clientId);
        handleClientTransferLifecycleEvent(client, client.getOffice(), TransferEventType.REJECTION, jsonCommand);
        this.clientRepositoryWrapper.saveAndFlush(client);
        return new CommandProcessingResultBuilder() 
                .withClientId(clientId) 
                .withEntityId(clientId) 
                .build();
    }
    private void handleClientTransferLifecycleEvent(final Client client, final Office destinationOffice,
            final TransferEventType transferEventType, final JsonCommand jsonCommand) {
        Staff staff = null;
        Group destinationGroup = null;
        final Long staffId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.newStaffIdParamName);
        final Long destinationGroupId = jsonCommand.longValueOfParameterNamed(TransferApiConstants.destinationGroupIdParamName);
        final LocalDate transferDate = jsonCommand.localDateValueOfParameterNamed(TransferApiConstants.transferDate);
        if (staffId != null) {
            staff = this.staffRepositoryWrapper.findByOfficeHierarchyWithNotFoundDetection(staffId, destinationOffice.getHierarchy());
        }
        if (transferEventType.isAcceptance() && destinationGroupId != null) {
            destinationGroup = this.groupRepository.findByOfficeWithNotFoundDetection(destinationGroupId, destinationOffice);
        }
        if (this.loanRepositoryWrapper.doNonClosedLoanAccountsExistForClient(client.getId())) {
            for (final Loan loan : this.loanRepositoryWrapper.findLoanByClientId(client.getId())) {
                if (loan.isDisbursed() && !loan.isClosed()) {
                    switch (transferEventType) {
                        case ACCEPTANCE:
                            this.loanWritePlatformService.acceptLoanTransfer(loan, loan.getLastUserTransactionDate(), destinationOffice,
                                    staff);
                        break;
                        case PROPOSAL:
                            this.loanWritePlatformService.initiateLoanTransfer(loan, transferDate);
                        break;
                        case REJECTION:
                            this.loanWritePlatformService.rejectLoanTransfer(loan);
                        break;
                        case WITHDRAWAL:
                            this.loanWritePlatformService.withdrawLoanTransfer(loan, loan.getLastUserTransactionDate());
                    }
                }
            }
        }
        if (this.savingsAccountRepositoryWrapper.doNonClosedSavingAccountsExistForClient(client.getId())) {
            for (final SavingsAccount savingsAccount : this.savingsAccountRepositoryWrapper.findSavingAccountByClientId(client.getId())) {
                if (savingsAccount.isActivated() && !savingsAccount.isClosed()) {
                    switch (transferEventType) {
                        case ACCEPTANCE:
                            this.savingsAccountWritePlatformService.acceptSavingsTransfer(savingsAccount,
                                    savingsAccount.retrieveLastTransactionDate(), destinationOffice, staff);
                        break;
                        case PROPOSAL:
                            this.savingsAccountWritePlatformService.initiateSavingsTransfer(savingsAccount, transferDate);
                        break;
                        case REJECTION:
                            this.savingsAccountWritePlatformService.rejectSavingsTransfer(savingsAccount);
                        break;
                        case WITHDRAWAL:
                            this.savingsAccountWritePlatformService.withdrawSavingsTransfer(savingsAccount,
                                    savingsAccount.retrieveLastTransactionDate());
                    }
                }
            }
        }
        switch (transferEventType) {
            case ACCEPTANCE:
                client.setStatus(ClientStatus.ACTIVE.getValue());
                client.updateTransferToOffice(null);
                client.updateOffice(destinationOffice);
                client.updateOfficeJoiningDate(client.getProposedTransferDate());
                client.updateProposedTransferDate(null);
                if (client.getGroups().size() == 1) {
                    if (destinationGroup == null) {
                        throw new TransferNotSupportedException(TransferNotSupportedReason.CLIENT_DESTINATION_GROUP_NOT_SPECIFIED,
                                client.getId());
                    } else if (!destinationGroup.isActive()) {
                        throw new GroupNotActiveException(destinationGroup.getId());
                    }
                    transferClientBetweenGroups(client.getGroups().iterator().next(), client, destinationGroup, true, staff);
                } else if (client.getGroups().size() == 0 && destinationGroup != null) {
                    client.getGroups().add(destinationGroup);
                    client.updateStaff(destinationGroup.getStaff());
                    if (staff != null) {
                        client.updateStaff(staff);
                    }
                } else if (destinationGroup == null) {
                    if (staff != null) {
                        client.updateStaff(staff);
                    }
                }
            break;
            case PROPOSAL:
                client.setStatus(ClientStatus.TRANSFER_IN_PROGRESS.getValue());
                client.updateTransferToOffice(destinationOffice);
                client.updateProposedTransferDate(transferDate);
            break;
            case REJECTION:
                client.setStatus(ClientStatus.TRANSFER_ON_HOLD.getValue());
                client.updateTransferToOffice(null);
                client.updateProposedTransferDate(null);
            break;
            case WITHDRAWAL:
                client.setStatus(ClientStatus.ACTIVE.getValue());
                client.updateTransferToOffice(null);
                client.updateProposedTransferDate(null);
        }
        this.noteWritePlatformService.createAndPersistClientNote(client, jsonCommand);
        this.clientTransferDetailsRepositoryWrapper
                .save(ClientTransferDetails.instance(client.getId(), client.getOffice().getId(), destinationOffice.getId(), transferDate,
                        transferEventType.getValue(), DateUtils.getBusinessLocalDate(), this.context.authenticatedUser().getId()));
    }
    private List<Client> assembleListOfClients(final JsonCommand command) {
        final List<Client> clients = new ArrayList<>();
        if (command.parameterExists(TransferApiConstants.clients)) {
            final JsonArray clientsArray = command.arrayOfParameterNamed(TransferApiConstants.clients);
            if (clientsArray != null) {
                for (int i = 0; i < clientsArray.size(); i++) {
                    final JsonObject jsonObject = clientsArray.get(i).getAsJsonObject();
                    if (jsonObject.has(TransferApiConstants.idParamName)) {
                        final Long id = jsonObject.get(TransferApiConstants.idParamName).getAsLong();
                        final Client client = this.clientRepositoryWrapper.findOneWithNotFoundDetection(id);
                        clients.add(client);
                    }
                }
            }
        }
        return clients;
    }
    private void validateClientAwaitingTransferAcceptance(final Client client) {
        if (!client.isTransferInProgress()) {
            throw new ClientNotAwaitingTransferApprovalException(client.getId());
        }
    }
    private void validateClientAwaitingTransferAcceptanceOnHold(final Client client) {
        if (!client.isTransferInProgressOrOnHold()) {
            throw new ClientNotAwaitingTransferApprovalOrOnHoldException(client.getId());
        }
    }
}
