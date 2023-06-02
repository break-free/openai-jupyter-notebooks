
package org.apache.fineract.organisation.provisioning.data;
import java.io.Serializable;
public class ProvisioningCategoryData implements Comparable<ProvisioningCategoryData>, Serializable {
    private final Long id;
    private final String categoryName;
    private final String categoryDescription;
    public ProvisioningCategoryData(final Long id, final String categoryName, final String categoryDescription) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }
    public Long getId() {
        return this.id;
    }
    public String getCategoryName() {
        return this.categoryName;
    }
    public String getCategoryDescription() {
        return this.categoryDescription;
    }
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof ProvisioningCategoryData)) {
            return false;
        }
        final ProvisioningCategoryData provisionCategoryData = (ProvisioningCategoryData) obj;
        return provisionCategoryData.id.equals(this.id);
    }
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    @Override
    public int compareTo(ProvisioningCategoryData obj) {
        if (obj == null) {
            return -1;
        }
        return obj.id.compareTo(this.id);
    }
}
