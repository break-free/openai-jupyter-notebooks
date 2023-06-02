
package org.apache.fineract.portfolio.collectionsheet.data;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
public final class IndividualCollectionSheetData {
    @SuppressWarnings("unused")
    private final LocalDate dueDate;
    @SuppressWarnings("unused")
    private final Collection<IndividualClientData> clients;
    @SuppressWarnings("unused")
    private final Collection<PaymentTypeData> paymentTypeOptions;
    public static IndividualCollectionSheetData instance(final LocalDate date, final Collection<IndividualClientData> clients,
            final Collection<PaymentTypeData> paymentTypeOptions) {
        return new IndividualCollectionSheetData(date, clients, paymentTypeOptions);
    }
    private IndividualCollectionSheetData(final LocalDate dueDate, final Collection<IndividualClientData> clients,
            final Collection<PaymentTypeData> paymentTypeOptions) {
        this.dueDate = dueDate;
        this.clients = clients;
        this.paymentTypeOptions = paymentTypeOptions;
    }
}
