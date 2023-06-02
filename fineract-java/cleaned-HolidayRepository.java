
package org.apache.fineract.organisation.holiday.domain;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
    @Query("select holiday from Holiday holiday, IN(holiday.offices) office where (holiday.fromDate >= :date OR :date <= holiday.toDate) and holiday.status = :status and office.id = :officeId")
    List<Holiday> findByOfficeIdAndGreaterThanDate(@Param("officeId") Long officeId, @Param("date") LocalDate date,
            @Param("status") Integer status);
    @Query("select holiday from Holiday holiday where holiday.processed = false and holiday.status = :status")
    List<Holiday> findUnprocessed(@Param("status") Integer status);
}
