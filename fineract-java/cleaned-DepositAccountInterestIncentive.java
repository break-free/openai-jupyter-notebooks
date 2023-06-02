
package org.apache.fineract.portfolio.savings.domain;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.interestratechart.domain.InterestIncentivesFields;
@Entity
@Table(name = "m_deposit_account_interest_incentives")
public class DepositAccountInterestIncentive extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "deposit_account_interest_rate_slab_id", nullable = false)
    private DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs;
    @Embedded
    private InterestIncentivesFields interestIncentivesFields;
    protected DepositAccountInterestIncentive() {
    }
    public DepositAccountInterestIncentive(final DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs,
            final InterestIncentivesFields interestIncentivesFields) {
        this.depositAccountInterestRateChartSlabs = depositAccountInterestRateChartSlabs;
        this.interestIncentivesFields = interestIncentivesFields;
    }
}
