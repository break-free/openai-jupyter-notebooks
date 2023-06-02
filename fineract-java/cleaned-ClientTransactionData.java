
package org.apache.fineract.portfolio.client.data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.organisation.monetary.data.CurrencyData;
import org.apache.fineract.portfolio.paymentdetail.data.PaymentDetailData;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
@SuppressWarnings("unused")
public final class ClientTransactionData {
    private final Long id;
    private final Long officeId;
    private final String officeName;
    private final EnumOptionData type;
    private final LocalDate date;
    private final CurrencyData currency;
    private final PaymentDetailData paymentDetailData;
    private final BigDecimal amount;
    private final String externalId;
    private final LocalDate submittedOnDate;
    private final boolean reversed;
    final Collection<PaymentTypeData> paymentTypeOptions;
    public static ClientTransactionData create(Long id, Long officeId, String officeName, EnumOptionData type, LocalDate date,
            CurrencyData currency, PaymentDetailData paymentDetailData, BigDecimal amount, String externalId, LocalDate submittedOnDate,
            boolean reversed) {
        final Collection<PaymentTypeData> paymentTypeOptions = null;
        return new ClientTransactionData(id, officeId, officeName, type, date, currency, paymentDetailData, amount, externalId,
                submittedOnDate, reversed, paymentTypeOptions);
    }
    private ClientTransactionData(Long id, Long officeId, String officeName, EnumOptionData type, LocalDate date, CurrencyData currency,
            PaymentDetailData paymentDetailData, BigDecimal amount, String externalId, LocalDate submittedOnDate, boolean reversed,
            Collection<PaymentTypeData> paymentTypeOptions) {
        this.id = id;
        this.officeId = officeId;
        this.officeName = officeName;
        this.type = type;
        this.date = date;
        this.currency = currency;
        this.paymentDetailData = paymentDetailData;
        this.amount = amount;
        this.externalId = externalId;
        this.submittedOnDate = submittedOnDate;
        this.reversed = reversed;
        this.paymentTypeOptions = paymentTypeOptions;
    }
}
