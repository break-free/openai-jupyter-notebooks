
package org.apache.fineract.portfolio.self.pockets.domain;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface PocketAccountMappingRepository
        extends JpaRepository<PocketAccountMapping, Long>, JpaSpecificationExecutor<PocketAccountMapping> {
    @Query("select pam from PocketAccountMapping pam where pam.id = :id and pam.pocketId =:pocketId")
    PocketAccountMapping findByIdAndPocketId(@Param("id") Long id, @Param("pocketId") Long pocketId);
    @Query("select pam from PocketAccountMapping pam where pam.pocketId =:pocketId")
    Collection<PocketAccountMapping> findByPocketId(@Param("pocketId") Long pocketId);
}
