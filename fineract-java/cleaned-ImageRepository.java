
package org.apache.fineract.infrastructure.documentmanagement.domain;
import org.apache.fineract.portfolio.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ImageRepository extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Client> {}
