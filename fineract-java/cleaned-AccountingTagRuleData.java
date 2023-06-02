
package org.apache.fineract.accounting.rule.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
@RequiredArgsConstructor
@Getter
public class AccountingTagRuleData {
    private final Long id;
    private final CodeValueData tag;
    private final EnumOptionData transactionType;
}
