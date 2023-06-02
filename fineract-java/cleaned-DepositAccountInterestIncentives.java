
package org.apache.fineract.portfolio.savings.domain;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.interestratechart.domain.InterestIncentivesFields;
@Entity
@Table(name = "m_savings_interest_incentives")
public class DepositAccountInterestIncentives extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "deposit_account_interest_rate_slab_id", nullable = false)
    private DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs;
    @Embedded
    private InterestIncentivesFields interestIncentivesFields;
    protected DepositAccountInterestIncentives() {
    }
    private DepositAccountInterestIncentives(final DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs,
            final InterestIncentivesFields interestIncentivesFields) {
        this.depositAccountInterestRateChartSlabs = depositAccountInterestRateChartSlabs;
        this.interestIncentivesFields = interestIncentivesFields;
    }
    public static DepositAccountInterestIncentives from(final DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs,
            final InterestIncentivesFields interestIncentivesFields) {
        return new DepositAccountInterestIncentives(depositAccountInterestRateChartSlabs, interestIncentivesFields);
    }
    public void updateDepositAccountInterestRateChartSlabs(DepositAccountInterestRateChartSlabs depositAccountInterestRateChartSlabs) {
        this.depositAccountInterestRateChartSlabs = depositAccountInterestRateChartSlabs;
    }
    public InterestIncentivesFields interestIncentivesFields() {
        return this.interestIncentivesFields;
    }
}
