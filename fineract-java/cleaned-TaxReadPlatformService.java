
package org.apache.fineract.portfolio.tax.service;
import java.util.Collection;
import org.apache.fineract.portfolio.tax.data.TaxComponentData;
import org.apache.fineract.portfolio.tax.data.TaxGroupData;
public interface TaxReadPlatformService {
    TaxComponentData retrieveTaxComponentData(Long id);
    TaxComponentData retrieveTaxComponentTemplate();
    TaxGroupData retrieveTaxGroupData(Long id);
    TaxGroupData retrieveTaxGroupWithTemplate(Long id);
    TaxGroupData retrieveTaxGroupTemplate();
    Collection<TaxComponentData> retrieveAllTaxComponents();
    Collection<TaxGroupData> retrieveAllTaxGroups();
    Collection<TaxGroupData> retrieveTaxGroupsForLookUp();
}
