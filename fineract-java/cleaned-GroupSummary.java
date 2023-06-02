
package org.apache.fineract.portfolio.group.data;
import java.util.Collection;
import org.apache.fineract.organisation.monetary.data.MoneyData;
public class GroupSummary {
    private final Long totalActiveClients;
    private final Long totalChildGroups;
    private final Collection<MoneyData> totalLoanPortfolio;
    private final Collection<MoneyData> totalSavings;
    public GroupSummary(final Long totalActiveClients, final Long totalChildGroups, final Collection<MoneyData> totalLoanPortfolio,
            final Collection<MoneyData> totalSavings) {
        this.totalActiveClients = totalActiveClients;
        this.totalChildGroups = totalChildGroups;
        this.totalLoanPortfolio = totalLoanPortfolio;
        this.totalSavings = totalSavings;
    }
    public Long getTotalActiveClients() {
        return this.totalActiveClients;
    }
    public Long getTotalChildGroups() {
        return this.totalChildGroups;
    }
    public Collection<MoneyData> getTotalLoanPortfolio() {
        return this.totalLoanPortfolio;
    }
    public Collection<MoneyData> getTotalSavings() {
        return this.totalSavings;
    }
}
