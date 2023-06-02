
package org.apache.fineract.accounting.journalentry.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class TransactionTypeEnumData {
    private final Long id;
    private final String code;
    private final String value;
}
