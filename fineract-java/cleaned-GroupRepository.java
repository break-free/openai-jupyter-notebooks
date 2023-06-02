
package org.apache.fineract.portfolio.group.domain;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Collection<Group> findByParentId(Long parentId);
    String RETRIEVE_SUBMITTED_ON_DATE = "select g.submittedOnDate from Group g where g.id = :groupId";
    @Query(RETRIEVE_SUBMITTED_ON_DATE)
    LocalDate retrieveGroupTypeSubmitteOndDate(@Param("groupId") Long groupId);
}
