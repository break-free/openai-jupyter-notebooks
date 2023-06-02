
package org.apache.fineract.useradministration.domain;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface AppUserPreviousPasswordRepository
        extends JpaRepository<AppUserPreviousPassword, Long>, JpaSpecificationExecutor<AppUserPreviousPassword> {
    List<AppUserPreviousPassword> findByUserId(Long userId, Pageable pageable);
}
