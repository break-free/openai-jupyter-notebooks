
package org.apache.fineract.portfolio.shareaccounts.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ShareAccountDividendRepository
        extends JpaRepository<ShareAccountDividendDetails, Long>, JpaSpecificationExecutor<ShareAccountDividendDetails> {
}
