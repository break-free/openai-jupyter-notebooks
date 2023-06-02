
package org.apache.fineract.portfolio.savings.domain;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.portfolio.paymentdetail.domain.PaymentDetail;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.transaction.annotation.Transactional;
public interface DepositAccountDomainService {
    SavingsAccountTransaction handleWithdrawal(SavingsAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, boolean applyWithdrawFee, boolean isRegularTransaction);
    SavingsAccountTransaction handleFDDeposit(FixedDepositAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail);
    SavingsAccountTransaction handleRDDeposit(RecurringDepositAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, boolean isRegularTransaction);
    SavingsAccountTransaction handleSavingDeposit(SavingsAccount account, DateTimeFormatter fmt, LocalDate transactionDate,
            BigDecimal transactionAmount, PaymentDetail paymentDetail, boolean isRegularTransaction);
    Long handleFDAccountClosure(FixedDepositAccount account, PaymentDetail paymentDetail, AppUser user, JsonCommand command,
            LocalDate tenantsTodayDate, Map<String, Object> changes);
    @Transactional
    Long handleFDAccountMaturityClosure(FixedDepositAccount account, PaymentDetail paymentDetail, AppUser user, LocalDate tenantsTodayDate,
            DateTimeFormatter fmt, LocalDate closedDate, Integer onAccountClosureId, Long toSavingsId, String transferDescription,
            Map<String, Object> changes);
    Long handleRDAccountClosure(RecurringDepositAccount account, PaymentDetail paymentDetail, AppUser user, JsonCommand command,
            LocalDate tenantsTodayDate, Map<String, Object> changes);
    Long handleFDAccountPreMatureClosure(FixedDepositAccount account, PaymentDetail paymentDetail, AppUser user, JsonCommand command,
            LocalDate tenantsTodayDate, Map<String, Object> changes);
    Long handleRDAccountPreMatureClosure(RecurringDepositAccount account, PaymentDetail paymentDetail, AppUser user, JsonCommand command,
            LocalDate tenantsTodayDate, Map<String, Object> changes);
}
