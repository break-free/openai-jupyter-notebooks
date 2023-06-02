
package org.apache.fineract.commands.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface CommandSourceRepository extends JpaRepository<CommandSource, Long>, JpaSpecificationExecutor<CommandSource> {
}
