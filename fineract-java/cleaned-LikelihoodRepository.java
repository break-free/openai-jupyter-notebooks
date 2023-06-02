
package org.apache.fineract.infrastructure.survey.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface LikelihoodRepository extends JpaRepository<Likelihood, Long>, JpaSpecificationExecutor<Likelihood> {
    @Query("select liklihood FROM Likelihood liklihood WHERE liklihood.ppiName =:ppiName AND liklihood.id <>:id")
    List<Likelihood> findByPpiNameAndLikeliHoodId(@Param("ppiName") String ppiName, @Param("id") Long likeliHoodId);
}
