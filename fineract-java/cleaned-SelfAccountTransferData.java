
package org.apache.fineract.portfolio.self.account.data;
import java.util.Collection;
@SuppressWarnings("unused")
public class SelfAccountTransferData {
    private final Collection<SelfAccountTemplateData> fromAccountOptions;
    private final Collection<SelfAccountTemplateData> toAccountOptions;
    public SelfAccountTransferData(final Collection<SelfAccountTemplateData> fromAccountOptions,
            Collection<SelfAccountTemplateData> toAccountOptions) {
        this.fromAccountOptions = fromAccountOptions;
        this.toAccountOptions = toAccountOptions;
    }
}
