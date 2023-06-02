
package org.apache.fineract.infrastructure.codes.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface CodeValueRepository extends JpaRepository<CodeValue, Long>, JpaSpecificationExecutor<CodeValue> {
    CodeValue findByCodeNameAndId(String codeName, Long id);
    CodeValue findByCodeNameAndLabel(String codeName, String label);
}
