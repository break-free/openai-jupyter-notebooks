
package org.apache.fineract.portfolio.group.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GroupRoleRepository extends JpaRepository<GroupRole, Long>, JpaSpecificationExecutor<GroupRole> {
}
