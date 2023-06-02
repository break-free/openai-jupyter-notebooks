
package org.apache.fineract.portfolio.self.account.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface SelfBeneficiariesTPTRepository
        extends JpaRepository<SelfBeneficiariesTPT, Long>, JpaSpecificationExecutor<SelfBeneficiariesTPT> {
}
