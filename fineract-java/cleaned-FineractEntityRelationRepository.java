
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface FineractEntityRelationRepository extends JpaRepository<FineractEntityRelation, Long> {
    @Query("select fea from FineractEntityRelation fea where fea.codeName= :codeName")
    FineractEntityRelation findOneByCodeName(@Param("codeName") String codeName);
}
