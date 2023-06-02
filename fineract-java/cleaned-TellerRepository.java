
package org.apache.fineract.organisation.teller.domain;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface TellerRepository extends JpaRepository<Teller, Long>, JpaSpecificationExecutor<Teller> {
    Collection<Teller> findTellerByOfficeId(Long officeId);
}
