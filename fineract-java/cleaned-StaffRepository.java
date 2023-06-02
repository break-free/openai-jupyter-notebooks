
package org.apache.fineract.organisation.staff.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {
    String FIND_BY_OFFICE_QUERY = "select s from Staff s where s.id = :id AND s.office.id = :officeId";
    @Query(FIND_BY_OFFICE_QUERY)
    Staff findByOffice(@Param("id") Long id, @Param("officeId") Long officeId);
}
