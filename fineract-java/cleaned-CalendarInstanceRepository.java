
package org.apache.fineract.portfolio.calendar.domain;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface CalendarInstanceRepository extends JpaRepository<CalendarInstance, Long>, JpaSpecificationExecutor<CalendarInstance> {
    CalendarInstance findByCalendarIdAndEntityIdAndEntityTypeId(Long calendarId, Long entityId, Integer entityTypeId);
    Collection<CalendarInstance> findByEntityIdAndEntityTypeId(Long entityId, Integer entityTypeId);
    CalendarInstance findByEntityIdAndEntityTypeIdAndCalendarTypeId(Long entityId, Integer entityTypeId, Integer calendarTypeId);
    @Query("select ci from CalendarInstance ci where ci.entityId = :entityId and ci.entityTypeId = :entityTypeId")
    CalendarInstance findCalendarInstaneByEntityId(@Param("entityId") Long entityId, @Param("entityTypeId") Integer entityTypeId);
    Collection<CalendarInstance> findByCalendarIdAndEntityTypeId(Long calendarId, Integer entityTypeId);
    @Query("select ci from CalendarInstance ci where ci.entityId in (select loan.id from Loan loan where loan.client.id = :clientId and loan.group.id = :groupId and (loan.loanStatus = 100 or loan.loanStatus = 200 or loan.loanStatus = 300)) and ci.entityTypeId = 3")
    List<CalendarInstance> findCalendarInstancesForActiveLoansByGroupIdAndClientId(@Param("groupId") Long groupId,
            @Param("clientId") Long clientId);
    @Query("SELECT COUNT(ci.id) FROM CalendarInstance ci, Loan ln WHERE ln.id = ci.entityId AND ci.entityTypeId = 3 AND ci.calendar.id = :calendarId AND ln.loanStatus IN :loanStatuses ")
    Integer countOfLoansSyncedWithCalendar(@Param("calendarId") Long calendarId, @Param("loanStatuses") Collection<Integer> loanStatuses);
}
