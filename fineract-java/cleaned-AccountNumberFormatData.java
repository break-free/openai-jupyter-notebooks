
package org.apache.fineract.infrastructure.accountnumberformat.data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public class AccountNumberFormatData implements Serializable {
    private final Long id;
    private final EnumOptionData accountType;
    private final EnumOptionData prefixType;
    private List<EnumOptionData> accountTypeOptions;
    private Map<String, List<EnumOptionData>> prefixTypeOptions;
    private String prefixCharacter;
    public AccountNumberFormatData(final Long id, final EnumOptionData accountType, final EnumOptionData prefixType,
            final String prefixCharacter) {
        this(id, accountType, prefixType, null, null, prefixCharacter);
    }
    public AccountNumberFormatData(final List<EnumOptionData> accountTypeOptions, Map<String, List<EnumOptionData>> prefixTypeOptions) {
        this(null, null, null, accountTypeOptions, prefixTypeOptions, null);
    }
    public void templateOnTop(List<EnumOptionData> accountTypeOptions, Map<String, List<EnumOptionData>> prefixTypeOptions) {
        this.accountTypeOptions = accountTypeOptions;
        this.prefixTypeOptions = prefixTypeOptions;
    }
    private AccountNumberFormatData(final Long id, final EnumOptionData accountType, final EnumOptionData prefixType,
            final List<EnumOptionData> accountTypeOptions, Map<String, List<EnumOptionData>> prefixTypeOptions,
            final String prefixCharacter) {
        this.id = id;
        this.accountType = accountType;
        this.prefixType = prefixType;
        this.accountTypeOptions = accountTypeOptions;
        this.prefixTypeOptions = prefixTypeOptions;
        this.prefixCharacter = prefixCharacter;
    }
    public Long getId() {
        return this.id;
    }
    public EnumOptionData getAccountType() {
        return this.accountType;
    }
    public EnumOptionData getPrefixType() {
        return this.prefixType;
    }
    public List<EnumOptionData> getAccountTypeOptions() {
        return this.accountTypeOptions;
    }
    public Map<String, List<EnumOptionData>> getPrefixTypeOptions() {
        return this.prefixTypeOptions;
    }
}
