
package org.apache.fineract.integrationtests.common.accounting;
import com.google.gson.Gson;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.fineract.integrationtests.common.Utils;
public class GLAccountBuilder {
    public static final String ASSET_ACCOUNT = "1";
    public static final String LIABILITY_ACCOUNT = "2";
    public static final String EQUITY_ACCOUNT = "3";
    public static final String INCOME_ACCOUNT = "4";
    public static final String EXPENSE_ACCOUNT = "5";
    private static final String ACCOUNT_USAGE_DETAIL = "1";
    private static final String ACCOUNT_USAGE_HEADER = "2";
    private static final String MANUAL_ENTRIES_ALLOW = "true";
    private static final String MANUAL_ENTRIES_NOT_ALLOW = "false";
    private static final String DESCRIPTION = "DEFAULT_DESCRIPTION";
    private String name = Utils.randomStringGenerator("ACCOUNT_NAME_", 5);
    private String glCode = "";
    private String accountType = "";
    private String accountUsage = ACCOUNT_USAGE_DETAIL;
    private String manualEntriesAllowed = MANUAL_ENTRIES_ALLOW;
    public String build() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("glCode", glCode);
        map.put("manualEntriesAllowed", manualEntriesAllowed);
        map.put("type", accountType);
        map.put("usage", accountUsage);
        map.put("description", DESCRIPTION);
        return new Gson().toJson(map);
    }
    public GLAccountBuilder withAccountTypeAsAsset() {
        accountType = ASSET_ACCOUNT;
        glCode = Utils.randomStringGenerator("ASSET_", 2);
        glCode += Calendar.getInstance().getTimeInMillis() + "";
        return this;
    }
    public GLAccountBuilder withAccountTypeAsLiability() {
        accountType = LIABILITY_ACCOUNT;
        glCode = Utils.randomStringGenerator("LIABILITY_", 2);
        glCode += Calendar.getInstance().getTimeInMillis() + "";
        return this;
    }
    public GLAccountBuilder withAccountTypeAsAsEquity() {
        accountType = EQUITY_ACCOUNT;
        glCode = Utils.randomStringGenerator("EQUITY_", 2);
        glCode += Calendar.getInstance().getTimeInMillis() + "";
        return this;
    }
    public GLAccountBuilder withAccountTypeAsIncome() {
        accountType = INCOME_ACCOUNT;
        glCode = Utils.randomStringGenerator("INCOME_", 2);
        glCode += Calendar.getInstance().getTimeInMillis() + "";
        return this;
    }
    public GLAccountBuilder withAccountTypeAsExpense() {
        accountType = EXPENSE_ACCOUNT;
        glCode = Utils.randomStringGenerator("EXPENSE_", 2);
        glCode += Calendar.getInstance().getTimeInMillis() + "";
        return this;
    }
    public GLAccountBuilder withAccountUsageAsHeader() {
        accountUsage = ACCOUNT_USAGE_HEADER;
        return this;
    }
    public GLAccountBuilder withMaualEntriesNotAllowed() {
        manualEntriesAllowed = MANUAL_ENTRIES_NOT_ALLOW;
        return this;
    }
}
