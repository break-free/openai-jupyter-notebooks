
package org.apache.fineract.infrastructure.campaigns.email.domain;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long>, JpaSpecificationExecutor<EmailMessage> {
    List<EmailMessage> findByStatusType(Integer emailMessageStatus);
}
