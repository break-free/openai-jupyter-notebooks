
package org.apache.fineract.portfolio.calendar.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface CalendarHistoryRepository extends JpaRepository<CalendarHistory, Long>, JpaSpecificationExecutor<CalendarHistory> {
}
