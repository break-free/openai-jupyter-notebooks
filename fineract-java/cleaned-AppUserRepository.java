
package org.apache.fineract.useradministration.domain;
import java.util.Collection;
import org.apache.fineract.infrastructure.security.domain.PlatformUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface AppUserRepository extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser>, PlatformUserRepository {
    @Query("Select appUser from AppUser appUser where appUser.username = :username")
    AppUser findAppUserByName(@Param("username") String username);
    Collection<AppUser> findByOfficeId(Long officeId);
}
