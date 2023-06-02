
package org.apache.fineract.portfolio.self.account.data;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class SelfBeneficiariesTPTData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private final String name;
    @SuppressWarnings("unused")
    private final String officeName;
    @SuppressWarnings("unused")
    private final String clientName;
    @SuppressWarnings("unused")
    private final EnumOptionData accountType;
    @SuppressWarnings("unused")
    private final String accountNumber;
    @SuppressWarnings("unused")
    private final Long transferLimit;
    @SuppressWarnings("unused")
    private final Collection<EnumOptionData> accountTypeOptions;
    public SelfBeneficiariesTPTData(final Collection<EnumOptionData> accountTypeOptions) {
        this.accountTypeOptions = accountTypeOptions;
        this.id = null;
        this.name = null;
        this.officeName = null;
        this.clientName = null;
        this.accountType = null;
        this.accountNumber = null;
        this.transferLimit = null;
    }
    public SelfBeneficiariesTPTData(final Long id, final String name, final String officeName, final String clientName,
            final EnumOptionData accountType, final String accountNumber, final Long transferLimit) {
        this.accountTypeOptions = null;
        this.id = id;
        this.name = name;
        this.officeName = officeName;
        this.clientName = clientName;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.transferLimit = transferLimit;
    }
}
