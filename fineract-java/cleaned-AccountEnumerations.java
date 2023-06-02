
package org.apache.fineract.portfolio.accountdetails.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.accountdetails.domain.AccountType;
public final class AccountEnumerations {
    private AccountEnumerations() {
    }
    public static EnumOptionData loanType(final Integer loanTypeId) {
        return loanType(AccountType.fromInt(loanTypeId));
    }
    public static EnumOptionData loanType(final String name) {
        return loanType(AccountType.fromName(name));
    }
    public static EnumOptionData loanType(final AccountType type) {
        EnumOptionData optionData = new EnumOptionData(AccountType.INVALID.getValue().longValue(), AccountType.INVALID.getCode(),
                "Invalid");
        switch (type) {
            case INVALID:
                optionData = new EnumOptionData(AccountType.INVALID.getValue().longValue(), AccountType.INVALID.getCode(), "Invalid");
            break;
            case INDIVIDUAL:
                optionData = new EnumOptionData(AccountType.INDIVIDUAL.getValue().longValue(), AccountType.INDIVIDUAL.getCode(),
                        "Individual");
            break;
            case GROUP:
                optionData = new EnumOptionData(AccountType.GROUP.getValue().longValue(), AccountType.GROUP.getCode(), "Group");
            break;
            case JLG:
                optionData = new EnumOptionData(AccountType.JLG.getValue().longValue(), AccountType.JLG.getCode(), "JLG");
            break;
            case GLIM:
                optionData = new EnumOptionData(AccountType.GLIM.getValue().longValue(), AccountType.GLIM.getCode(), "GLIM");
            break;
            case GSIM:
                optionData = new EnumOptionData(AccountType.GSIM.getValue().longValue(), AccountType.GSIM.getCode(), "GSIM");
            break;
        }
        return optionData;
    }
}
