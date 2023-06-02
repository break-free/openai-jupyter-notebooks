
package org.apache.fineract.infrastructure.dataqueries.data;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public enum EntityTables {
    CLIENT("m_client", ImmutableList.of(StatusEnum.CREATE.getCode(), StatusEnum.ACTIVATE.getCode(), StatusEnum.CLOSE.getCode()),
            "client_id"), LOAN("m_loan",
                    ImmutableList.of(StatusEnum.CREATE.getCode(), StatusEnum.APPROVE.getCode(), StatusEnum.DISBURSE.getCode(),
                            StatusEnum.WITHDRAWN.getCode(), StatusEnum.REJECTED.getCode(), StatusEnum.WRITE_OFF.getCode()),
                    "loan_id"), GROUP("m_group",
                            ImmutableList.of(StatusEnum.CREATE.getCode(), StatusEnum.ACTIVATE.getCode(), StatusEnum.CLOSE.getCode()),
                            "group_id"), SAVING("m_savings_account",
                                    ImmutableList.of(StatusEnum.CREATE.getCode(), StatusEnum.APPROVE.getCode(),
                                            StatusEnum.ACTIVATE.getCode(), StatusEnum.WITHDRAWN.getCode(), StatusEnum.REJECTED.getCode(),
                                            StatusEnum.CLOSE.getCode()),
                                    "savings_account_id");
    private static final Map<String, EntityTables> lookup = new HashMap<String, EntityTables>();
    static {
        for (EntityTables d : EntityTables.values()) {
            lookup.put(d.getName(), d);
        }
    }
    private final String name;
    private final ImmutableList<Integer> codes;
    private final String foreignKeyColumnNameOnDatatable;
    EntityTables(String name, ImmutableList<Integer> codes, String foreignKeyColumnNameOnDatatable) {
        this.name = name;
        this.codes = codes;
        this.foreignKeyColumnNameOnDatatable = foreignKeyColumnNameOnDatatable;
    }
    public static List<String> getEntitiesList() {
        List<String> data = new ArrayList<String>();
        for (EntityTables entity : EntityTables.values()) {
            data.add(entity.name);
        }
        return data;
    }
    public static ImmutableList<Integer> getStatus(String name) {
        if (lookup.get(name) != null) {
            return lookup.get(name).getCodes();
        }
        return ImmutableList.of();
    }
    public ImmutableList<Integer> getCodes() {
        return this.codes;
    }
    public String getName() {
        return name;
    }
    public String getForeignKeyColumnNameOnDatatable() {
        return this.foreignKeyColumnNameOnDatatable;
    }
    public static String getForeignKeyColumnNameOnDatatable(String name) {
        return lookup.get(name).foreignKeyColumnNameOnDatatable;
    }
}
