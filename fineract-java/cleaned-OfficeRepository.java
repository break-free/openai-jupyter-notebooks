
package org.apache.fineract.organisation.office.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface OfficeRepository extends JpaRepository<Office, Long>, JpaSpecificationExecutor<Office> {
}
