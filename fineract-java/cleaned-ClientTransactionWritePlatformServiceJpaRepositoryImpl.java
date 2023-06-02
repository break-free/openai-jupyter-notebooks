
package org.apache.fineract.portfolio.client.service;
import java.util.Map;
import java.util.Set;
import org.apache.fineract.accounting.journalentry.service.JournalEntryWritePlatformService;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.organisation.office.domain.OrganisationCurrencyRepositoryWrapper;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.client.domain.ClientCharge;
import org.apache.fineract.portfolio.client.domain.ClientChargePaidBy;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.client.domain.ClientTransaction;
import org.apache.fineract.portfolio.client.domain.ClientTransactionRepositoryWrapper;
import org.apache.fineract.portfolio.client.exception.ClientTransactionCannotBeUndoneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ClientTransactionWritePlatformServiceJpaRepositoryImpl implements ClientTransactionWritePlatformService {
    private final ClientTransactionRepositoryWrapper clientTransactionRepository;
    private final ClientRepositoryWrapper clientRepository;
    private final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepository;
    private final JournalEntryWritePlatformService journalEntryWritePlatformService;
    @Autowired
    public ClientTransactionWritePlatformServiceJpaRepositoryImpl(final ClientTransactionRepositoryWrapper clientTransactionRepository,
            final ClientRepositoryWrapper clientRepositoryWrapper,
            final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepositoryWrapper,
            JournalEntryWritePlatformService journalEntryWritePlatformService) {
        this.clientTransactionRepository = clientTransactionRepository;
        this.clientRepository = clientRepositoryWrapper;
        this.organisationCurrencyRepository = organisationCurrencyRepositoryWrapper;
        this.journalEntryWritePlatformService = journalEntryWritePlatformService;
    }
    @Override
    public CommandProcessingResult undo(Long clientId, Long transactionId) {
        final Client client = this.clientRepository.getActiveClientInUserScope(clientId);
        final ClientTransaction clientTransaction = this.clientTransactionRepository.findOneWithNotFoundDetection(clientId, transactionId);
        if (clientTransaction.isReversed()) {
            throw new ClientTransactionCannotBeUndoneException(clientId, transactionId);
        }
        clientTransaction.reverse();
        if (clientTransaction.isPayChargeTransaction() || clientTransaction.isWaiveChargeTransaction()) {
            final Set<ClientChargePaidBy> chargesPaidBy = clientTransaction.getClientChargePaidByCollection();
            for (final ClientChargePaidBy clientChargePaidBy : chargesPaidBy) {
                final ClientCharge clientCharge = clientChargePaidBy.getClientCharge();
                clientCharge.setCurrency(
                        organisationCurrencyRepository.findOneWithNotFoundDetection(clientCharge.getCharge().getCurrencyCode()));
                if (clientTransaction.isPayChargeTransaction()) {
                    clientCharge.undoPayment(clientTransaction.getAmount());
                } else if (clientTransaction.isWaiveChargeTransaction()) {
                    clientCharge.undoWaiver(clientTransaction.getAmount());
                }
            }
        }
        this.clientTransactionRepository.saveAndFlush(clientTransaction);
        generateAccountingEntries(clientTransaction);
        return new CommandProcessingResultBuilder() 
                .withEntityId(transactionId) 
                .withOfficeId(client.officeId()) 
                .withClientId(clientId) 
                .build();
    }
    private void generateAccountingEntries(ClientTransaction clientTransaction) {
        Map<String, Object> accountingBridgeData = clientTransaction.toMapData();
        journalEntryWritePlatformService.createJournalEntriesForClientTransactions(accountingBridgeData);
    }
}
