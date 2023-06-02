
package org.apache.fineract.integrationtests.common.system;
import com.google.gson.Gson;
import java.util.HashMap;
public class AccountNumberPreferencesTestBuilder {
    private String clientAccountType = "1";
    private String clientPrefixType = "101";
    private String loanAccountType = "2";
    private String loanPrefixType = "1";
    private String savingsAccountType = "3";
    private String savingsPrefixType = "1";
    private String centerAccountType = "4";
    private String centerPrefixType = "1";
    private String groupsAccountType = "5";
    private String groupsPrefixType = "1";
    public String clientBuild() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", clientAccountType);
        map.put("prefixType", clientPrefixType);
        return new Gson().toJson(map);
    }
    public String loanBuild() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", loanAccountType);
        map.put("prefixType", loanPrefixType);
        return new Gson().toJson(map);
    }
    public String savingsBuild() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", savingsAccountType);
        map.put("prefixType", savingsPrefixType);
        return new Gson().toJson(map);
    }
    public String groupsBuild() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", groupsAccountType);
        map.put("prefixType", groupsPrefixType);
        return new Gson().toJson(map);
    }
    public String centerBuild() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", centerAccountType);
        map.put("prefixType", centerPrefixType);
        return new Gson().toJson(map);
    }
    public String invalidDataBuild(String accountType, String prefixType) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("accountType", accountType);
        map.put("prefixType", prefixType);
        return new Gson().toJson(map);
    }
    public String updatePrefixType(final String prefixType) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("prefixType", prefixType);
        return new Gson().toJson(map);
    }
}
