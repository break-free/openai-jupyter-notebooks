
package org.apache.fineract.infrastructure.dataqueries.data;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.fineract.portfolio.loanproduct.data.LoanProductData;
import org.apache.fineract.portfolio.savings.data.SavingsProductData;
public class EntityDataTableChecksTemplateData implements Serializable {
    private final List<String> entities;
    private final List<DatatableCheckStatusData> statusClient;
    private final List<DatatableCheckStatusData> statusGroup;
    private final List<DatatableCheckStatusData> statusSavings;
    private final List<DatatableCheckStatusData> statusLoans;
    private final List<DatatableChecksData> datatables;
    private final Collection<LoanProductData> loanProductDatas;
    private final Collection<SavingsProductData> savingsProductDatas;
    public EntityDataTableChecksTemplateData(final List<String> entities, List<DatatableCheckStatusData> statusClient,
            List<DatatableCheckStatusData> statusGroup, List<DatatableCheckStatusData> statusSavings,
            List<DatatableCheckStatusData> statusLoans, List<DatatableChecksData> datatables, Collection<LoanProductData> loanProductDatas,
            Collection<SavingsProductData> savingsProductDatas) {
        this.entities = entities;
        this.statusClient = statusClient;
        this.statusGroup = statusGroup;
        this.statusSavings = statusSavings;
        this.statusLoans = statusLoans;
        this.datatables = datatables;
        this.loanProductDatas = loanProductDatas;
        this.savingsProductDatas = savingsProductDatas;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityDataTableChecksTemplateData)) {
            return false;
        }
        EntityDataTableChecksTemplateData that = (EntityDataTableChecksTemplateData) o;
        return Objects.equals(entities, that.entities) && Objects.equals(statusClient, that.statusClient)
                && Objects.equals(statusGroup, that.statusGroup) && Objects.equals(statusSavings, that.statusSavings)
                && Objects.equals(statusLoans, that.statusLoans) && Objects.equals(datatables, that.datatables)
                && CollectionUtils.isEqualCollection(loanProductDatas, that.loanProductDatas)
                && CollectionUtils.isEqualCollection(savingsProductDatas, that.savingsProductDatas);
    }
    @Override
    public int hashCode() {
        return Objects.hash(entities, statusClient, statusGroup, statusSavings, statusLoans, datatables, loanProductDatas,
                savingsProductDatas);
    }
}
