
package org.apache.fineract.portfolio.loanaccount.domain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import org.apache.fineract.portfolio.charge.domain.Charge;
import org.apache.fineract.portfolio.charge.domain.ChargeCalculationType;
import org.apache.fineract.portfolio.charge.domain.ChargePaymentMode;
import org.apache.fineract.portfolio.charge.domain.ChargeTimeType;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
public class LoanTest {
    @Test
    public void testGetChargesWithCharges() {
        Loan loan = new Loan();
        ReflectionTestUtils.setField(loan, "charges", Collections.singleton(buildLoanCharge()));
        final Collection<LoanCharge> chargeIds = loan.getCharges();
        assertEquals(1, chargeIds.size());
    }
    @Test
    public void testGetChargesWithNoCharges() {
        final Loan loan = new Loan();
        final Collection<LoanCharge> chargeIds = loan.getCharges();
        assertEquals(0, chargeIds.size());
    }
    @Test
    public void testGetChargesWithNull() {
        final Loan loan = new Loan();
        ReflectionTestUtils.setField(loan, "charges", null);
        final Collection<LoanCharge> chargeIds = loan.getCharges();
        assertEquals(0, chargeIds.size());
    }
    private LoanCharge buildLoanCharge() {
        return new LoanCharge(mock(Loan.class), mock(Charge.class), new BigDecimal(100), new BigDecimal(100),
                ChargeTimeType.TRANCHE_DISBURSEMENT, ChargeCalculationType.FLAT, LocalDate.of(2022, 6, 27), ChargePaymentMode.REGULAR, 1,
                new BigDecimal(100));
    }
}
