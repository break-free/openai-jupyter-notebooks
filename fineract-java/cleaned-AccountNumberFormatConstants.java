
package org.apache.fineract.infrastructure.accountnumberformat.service;
import org.apache.fineract.infrastructure.accountnumberformat.data.AccountNumberFormatData;
public final class AccountNumberFormatConstants {
    private AccountNumberFormatConstants() {
    }
    public static final String ENTITY_NAME = "accountNumberFormat";
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";
    public static final String resourceRelativeURL = "/accountnumberformats";
    public static final String idParamName = "id";
    public static final String accountTypeParamName = "accountType";
    public static final String prefixTypeParamName = "prefixType";
    public static final String prefixCharacterParamName = "prefixCharacter";
    public static final String accountTypeOptionsParamName = "accountTypeOptions";
    public static final String prefixTypeOptionsParamName = "prefixTypeOptions";
    public static final String EXCEPTION_DUPLICATE_ACCOUNT_TYPE = "error.msg.account.number.format.duplicate.account.type";
    public static final String EXCEPTION_ACCOUNT_NUMBER_FORMAT_NOT_FOUND = "error.msg.account.number.format.id.invalid";
    public static final String ACCOUNT_NUMBER_FORMAT_TABLE_NAME = "c_account_number_format";
    public static final String ACCOUNT_TYPE_ENUM_COLUMN_NAME = "account_type_enum";
    public static final String PREFIX_TYPE_ENUM_COLUMN_NAME = "prefix_type_enum";
    public static final String ACCOUNT_TYPE_UNIQUE_CONSTRAINT_NAME = "account_type_enum";
    public static final String PREFIX_CHARACTER_COLUMN_NAME = "prefix_character";
}
