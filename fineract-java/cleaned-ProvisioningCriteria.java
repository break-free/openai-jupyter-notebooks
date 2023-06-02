
package org.apache.fineract.organisation.provisioning.domain;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractAuditableCustom;
import org.apache.fineract.organisation.provisioning.constants.ProvisioningCriteriaConstants;
import org.apache.fineract.organisation.provisioning.data.ProvisioningCriteriaDefinitionData;
import org.apache.fineract.portfolio.loanproduct.domain.LoanProduct;
import org.apache.fineract.useradministration.domain.AppUser;
@Entity
@Table(name = "m_provisioning_criteria", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "criteria_name" }, name = "criteria_name") })
public class ProvisioningCriteria extends AbstractAuditableCustom {
    @Column(name = "criteria_name", nullable = false)
    private String criteriaName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criteria", orphanRemoval = true, fetch = FetchType.EAGER)
    Set<ProvisioningCriteriaDefinition> provisioningCriteriaDefinition = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "criteria", orphanRemoval = true, fetch = FetchType.EAGER)
    Set<LoanProductProvisionCriteria> loanProductMapping = new HashSet<>();
    public String getCriteriaName() {
        return this.criteriaName;
    }
    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }
    protected ProvisioningCriteria() {
    }
    public ProvisioningCriteria(String criteriaName, AppUser createdBy, LocalDateTime createdDate, AppUser lastModifiedBy,
            LocalDateTime lastModifiedDate) {
        this.criteriaName = criteriaName;
        setCreatedBy(createdBy.getId());
        setCreatedDate(createdDate);
        setLastModifiedBy(lastModifiedBy.getId());
        setLastModifiedDate(lastModifiedDate);
    }
    public void setProvisioningCriteriaDefinitions(Set<ProvisioningCriteriaDefinition> provisioningCriteriaDefinition) {
        this.provisioningCriteriaDefinition.clear();
        this.provisioningCriteriaDefinition.addAll(provisioningCriteriaDefinition);
    }
    public void setLoanProductProvisioningCriteria(Set<LoanProductProvisionCriteria> loanProductMapping) {
        this.loanProductMapping.clear();
        this.loanProductMapping.addAll(loanProductMapping);
    }
    public Map<String, Object> update(JsonCommand command, List<LoanProduct> loanProducts) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);
        if (command.isChangeInStringParameterNamed(ProvisioningCriteriaConstants.JSON_CRITERIANAME_PARAM, criteriaName)) {
            final String valueAsInput = command.stringValueOfParameterNamed(ProvisioningCriteriaConstants.JSON_CRITERIANAME_PARAM);
            actualChanges.put(ProvisioningCriteriaConstants.JSON_CRITERIANAME_PARAM, valueAsInput);
            this.criteriaName = valueAsInput;
        }
        Set<LoanProductProvisionCriteria> temp = new HashSet<>();
        Set<LoanProduct> productsTemp = new HashSet<>();
        for (LoanProductProvisionCriteria mapping : loanProductMapping) {
            if (!loanProducts.contains(mapping.getLoanProduct())) {
                temp.add(mapping);
            } else {
                productsTemp.add(mapping.getLoanProduct());
            }
        }
        loanProductMapping.removeAll(temp);
        for (LoanProduct loanProduct : loanProducts) {
            if (!productsTemp.contains(loanProduct)) {
                this.loanProductMapping.add(new LoanProductProvisionCriteria(this, loanProduct));
            }
        }
        actualChanges.put(ProvisioningCriteriaConstants.JSON_LOANPRODUCTS_PARAM, loanProductMapping);
        return actualChanges;
    }
    public void update(ProvisioningCriteriaDefinitionData data, GLAccount liability, GLAccount expense) {
        for (ProvisioningCriteriaDefinition def : provisioningCriteriaDefinition) {
            if (data.getId().equals(def.getId())) {
                def.update(data.getMinAge(), data.getMaxAge(), data.getProvisioningPercentage(), liability, expense);
                break;
            }
        }
    }
}
