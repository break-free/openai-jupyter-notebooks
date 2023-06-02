
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface FineractEntityAccessRepository
        extends JpaRepository<FineractEntityAccess, Long>, JpaSpecificationExecutor<FineractEntityAccess> {
}
