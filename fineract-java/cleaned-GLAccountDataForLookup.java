
package org.apache.fineract.accounting.glaccount.data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Getter
public class GLAccountDataForLookup {
    private final Long id;
    private final String name;
    private final String glCode;
}
