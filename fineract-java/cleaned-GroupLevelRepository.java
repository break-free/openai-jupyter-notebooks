
package org.apache.fineract.portfolio.group.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GroupLevelRepository extends JpaRepository<GroupLevel, Long>, JpaSpecificationExecutor<GroupLevel> {
    GroupLevel findBySuperParent(boolean superParent);
    GroupLevel findByParentId(Long parentId);
}
