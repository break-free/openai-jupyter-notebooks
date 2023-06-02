
package org.apache.fineract.infrastructure.sms.domain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface SmsMessageRepository extends JpaRepository<SmsMessage, Long>, JpaSpecificationExecutor<SmsMessage> {
    Page<SmsMessage> findByStatusType(Integer status, Pageable pageable);
}
