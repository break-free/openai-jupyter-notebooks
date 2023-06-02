
package org.apache.fineract.integrationtests.common.accounting;
import com.google.gson.Gson;
import java.util.HashMap;
public final class FinancialActivityAccountsMappingBuilder {
    private FinancialActivityAccountsMappingBuilder() {
    }
    public static String build(Integer financialActivityId, Integer glAccountId) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("financialActivityId", financialActivityId);
        map.put("glAccountId", glAccountId);
        return new Gson().toJson(map);
    }
}
