
package org.apache.fineract.accounting.journalentry.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.portfolio.note.data.NoteData;
import org.apache.fineract.portfolio.paymentdetail.data.PaymentDetailData;
@RequiredArgsConstructor
@Getter
public class TransactionDetailData {
    private final Long transactionId;
    private final PaymentDetailData paymentDetails;
    private final NoteData noteData;
    private final TransactionTypeEnumData transactionType;
    public TransactionDetailData(final Long transactionId, final PaymentDetailData paymentDetails, final NoteData noteData) {
        this.transactionId = transactionId;
        this.paymentDetails = paymentDetails;
        this.noteData = noteData;
        this.transactionType = null;
    }
}
