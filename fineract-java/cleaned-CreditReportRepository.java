
package org.apache.fineract.infrastructure.creditbureau.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface CreditReportRepository extends JpaRepository<CreditReport, Long>, JpaSpecificationExecutor<CreditReport> {
    @Query("SELECT creditBureauReport from CreditReport creditBureauReport where creditBureauReport.nationalId = :nationalId and creditBureauReport.creditBureauId = :creditBureauId ")
    CreditReport getThitsaWorksCreditReport(@Param("creditBureauId") Long creditBureauId, @Param("nationalId") String nationalId);
}
