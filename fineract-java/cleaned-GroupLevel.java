
package org.apache.fineract.portfolio.group.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_group_level")
public class GroupLevel extends AbstractPersistableCustom {
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "super_parent", nullable = false)
    private boolean superParent;
    @Column(name = "level_name", nullable = false, length = 100, unique = true)
    private String levelName;
    @Column(name = "recursable", nullable = false)
    private boolean recursable = false;
    @Column(name = "can_have_clients", nullable = false)
    private boolean canHaveClients = false;
    public GroupLevel() {
        this.parentId = null;
        this.superParent = false;
        this.levelName = null;
        this.recursable = false;
        this.canHaveClients = false;
    }
    public GroupLevel(final Long parentId, final boolean isSuperParent, final String levelName, final boolean recursable,
            final boolean canHaveClients) {
        this.superParent = isSuperParent;
        this.parentId = parentId;
        this.levelName = levelName;
        this.recursable = recursable;
        this.canHaveClients = canHaveClients;
    }
    public Long getParentId() {
        return this.parentId;
    }
    public String getLevelName() {
        return this.levelName;
    }
    public boolean isRecursable() {
        return this.recursable;
    }
    public boolean canHaveClients() {
        return this.canHaveClients;
    }
    public boolean isSuperParent() {
        return this.superParent;
    }
    public boolean isIdentifiedByParentId(final Long parentLevelId) {
        return this.parentId.equals(parentLevelId);
    }
    public boolean isCenter() {
        return this.levelName.equalsIgnoreCase("Center");
    }
    public boolean isGroup() {
        return this.levelName.equalsIgnoreCase("Group");
    }
}
