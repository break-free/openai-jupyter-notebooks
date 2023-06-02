
package org.apache.fineract.infrastructure.creditbureau.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
public interface TokenRepository extends JpaRepository<CreditBureauToken, Long>, JpaSpecificationExecutor<CreditBureauToken> {
    @Query("select creditbureautoken from CreditBureauToken creditbureautoken")
    CreditBureauToken getToken();
}
