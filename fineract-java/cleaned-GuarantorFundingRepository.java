
package org.apache.fineract.portfolio.loanaccount.guarantor.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GuarantorFundingRepository
        extends JpaRepository<GuarantorFundingDetails, Long>, JpaSpecificationExecutor<GuarantorFundingDetails> {}
