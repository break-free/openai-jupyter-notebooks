
package org.apache.fineract.infrastructure.bulkimport.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ImportDocumentRepository extends JpaRepository<ImportDocument, Long>, JpaSpecificationExecutor<ImportDocument> {
}
