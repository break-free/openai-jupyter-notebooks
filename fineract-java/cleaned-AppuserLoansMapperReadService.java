
package org.apache.fineract.portfolio.self.loanaccount.service;
public interface AppuserLoansMapperReadService {
    Boolean isLoanMappedToUser(Long loanId, Long appUserId);
}
