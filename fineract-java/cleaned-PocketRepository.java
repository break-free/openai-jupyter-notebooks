
package org.apache.fineract.portfolio.self.pockets.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface PocketRepository extends JpaRepository<Pocket, Long>, JpaSpecificationExecutor<Pocket> {
    @Query("select pocket.id from Pocket pocket where pocket.appUserId= :appUserId")
    Long findByAppUserId(@Param("appUserId") Long appUserId);
}
