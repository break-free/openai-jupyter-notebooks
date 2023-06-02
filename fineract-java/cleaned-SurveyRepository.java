
package org.apache.fineract.spm.domain;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    @Query("select s from Survey s where :pointInTime between s.validFrom and s.validTo")
    List<Survey> fetchActiveSurveys(@Param("pointInTime") LocalDateTime pointInTime);
    @Query("select s from Survey s ")
    List<Survey> fetchAllSurveys();
    @Query("select s from Survey s where s.key = :key and :pointInTime between s.validFrom and s.validTo")
    Survey findByKey(@Param("key") String key, @Param("pointInTime") LocalDateTime pointInTime);
}
