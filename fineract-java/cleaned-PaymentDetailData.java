
package org.apache.fineract.portfolio.paymentdetail.data;
import java.io.Serializable;
import java.util.Objects;
import org.apache.fineract.portfolio.paymenttype.data.PaymentTypeData;
public class PaymentDetailData implements Serializable {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final PaymentTypeData paymentType;
    @SuppressWarnings("unused")
    private final String accountNumber;
    @SuppressWarnings("unused")
    private final String checkNumber;
    @SuppressWarnings("unused")
    private final String routingCode;
    @SuppressWarnings("unused")
    private final String receiptNumber;
    @SuppressWarnings("unused")
    private final String bankNumber;
    public PaymentDetailData(final Long id, final PaymentTypeData paymentType, final String accountNumber, final String checkNumber,
            final String routingCode, final String receiptNumber, final String bankNumber) {
        this.id = id;
        this.paymentType = paymentType;
        this.accountNumber = accountNumber;
        this.checkNumber = checkNumber;
        this.routingCode = routingCode;
        this.receiptNumber = receiptNumber;
        this.bankNumber = bankNumber;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetailData)) {
            return false;
        }
        PaymentDetailData that = (PaymentDetailData) o;
        return Objects.equals(id, that.id) && Objects.equals(paymentType, that.paymentType)
                && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(checkNumber, that.checkNumber)
                && Objects.equals(routingCode, that.routingCode) && Objects.equals(receiptNumber, that.receiptNumber)
                && Objects.equals(bankNumber, that.bankNumber);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, paymentType, accountNumber, checkNumber, routingCode, receiptNumber, bankNumber);
    }
    public PaymentTypeData getPaymentType() {
        return this.paymentType;
    }
}
