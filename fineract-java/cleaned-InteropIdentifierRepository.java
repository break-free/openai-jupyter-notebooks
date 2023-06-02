
package org.apache.fineract.interoperation.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface InteropIdentifierRepository extends JpaRepository<InteropIdentifier, Long>, JpaSpecificationExecutor<InteropIdentifier> {
    InteropIdentifier findOneByTypeAndValueAndSubType(InteropIdentifierType idType, String value, String subType);
}
