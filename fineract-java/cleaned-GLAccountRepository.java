
package org.apache.fineract.accounting.glaccount.domain;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GLAccountRepository extends JpaRepository<GLAccount, Long>, JpaSpecificationExecutor<GLAccount> {
    Optional<GLAccount> findOneByGlCode(String glCode);
}
