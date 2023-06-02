
package org.apache.fineract.organisation.teller.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface TellerTransactionRepository extends JpaRepository<TellerTransaction, Long>, JpaSpecificationExecutor<TellerTransaction> {
}
