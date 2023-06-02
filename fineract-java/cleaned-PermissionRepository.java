
package org.apache.fineract.useradministration.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p FROM Permission p WHERE LOWER(TRIM(BOTH FROM p.code)) = LOWER(TRIM(BOTH FROM ?1))")
    Permission findOneByCode(String code);
}
