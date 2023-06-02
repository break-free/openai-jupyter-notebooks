
package org.apache.fineract.template.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findByEntityAndType(TemplateEntity entity, TemplateType templateType);
    @Query("select t from Template as t left join t.mappers as m where m.mapperkey = :mapperkey and m.mappervalue = :mappervalue")
    List<Template> findByTemplateMapper(@Param("mapperkey") String mapperkey, @Param("mappervalue") String mappervalue);
}
