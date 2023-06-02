
package org.apache.fineract.spm.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LookupTableRepository extends JpaRepository<LookupTable, Long> {
    List<LookupTable> findBySurvey(Survey survey);
    List<LookupTable> findByKey(String spmKey);
}
