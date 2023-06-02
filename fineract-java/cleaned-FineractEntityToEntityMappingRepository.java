
package org.apache.fineract.infrastructure.entityaccess.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface FineractEntityToEntityMappingRepository extends JpaRepository<FineractEntityToEntityMapping, Long> {
    @Query("select feem from FineractEntityToEntityMapping feem where feem.fromId= :fromId and feem.toId= :toId and feem.relationId= :relId")
    FineractEntityToEntityMapping findListByProductId(@Param("relId") FineractEntityRelation relId, @Param("toId") Long toId,
            @Param("fromId") Long fromId);
}
