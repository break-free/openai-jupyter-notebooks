
package org.apache.fineract.portfolio.shareaccounts.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ShareAccountRepository extends JpaRepository<ShareAccount, Long>, JpaSpecificationExecutor<ShareAccount> {
}
