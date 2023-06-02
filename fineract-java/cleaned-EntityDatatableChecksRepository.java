
package org.apache.fineract.infrastructure.dataqueries.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EntityDatatableChecksRepository
        extends JpaRepository<EntityDatatableChecks, Long>, JpaSpecificationExecutor<EntityDatatableChecks> {
    List<EntityDatatableChecks> findByEntityAndStatus(String entityName, Long status);
    @Query("select t from  EntityDatatableChecks t WHERE t.status =:status and t.entity=:entity and t.productId = :productId ")
    List<EntityDatatableChecks> findByEntityStatusAndProduct(@Param("entity") String entity, @Param("status") Long status,
            @Param("productId") Long productId);
    @Query("select t from  EntityDatatableChecks t WHERE t.status =:status and t.entity=:entity and t.productId IS NULL ")
    List<EntityDatatableChecks> findByEntityStatusAndNoProduct(@Param("entity") String entity, @Param("status") Long status);
    @Query("select t from  EntityDatatableChecks t WHERE t.status =:status "
            + "and t.entity=:entity and t.datatableName = :datatableName AND t.productId = :productId")
    List<EntityDatatableChecks> findByEntityStatusAndDatatableIdAndProductId(@Param("entity") String entityName,
            @Param("status") Long status, @Param("datatableName") String datatableName, @Param("productId") Long productId);
    @Query("select t from  EntityDatatableChecks t WHERE t.status =:status and t.entity=:entity "
            + " and t.datatableName = :datatableName AND t.productId IS NULL")
    List<EntityDatatableChecks> findByEntityStatusAndDatatableIdAndNoProduct(@Param("entity") String entityName,
            @Param("status") Long status, @Param("datatableName") String datatableName);
}
