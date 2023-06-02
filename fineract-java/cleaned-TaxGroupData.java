
package org.apache.fineract.portfolio.tax.data;
import java.io.Serializable;
import java.util.Collection;
public final class TaxGroupData implements Serializable {
    private final Long id;
    private final String name;
    private final Collection<TaxGroupMappingsData> taxAssociations;
    @SuppressWarnings("unused")
    private final Collection<TaxComponentData> taxComponents;
    public static TaxGroupData instance(final Long id, final String name, final Collection<TaxGroupMappingsData> taxAssociations) {
        final Collection<TaxComponentData> taxComponents = null;
        return new TaxGroupData(id, name, taxAssociations, taxComponents);
    }
    public static TaxGroupData lookup(final Long id, final String name) {
        final Collection<TaxComponentData> taxComponents = null;
        final Collection<TaxGroupMappingsData> taxAssociations = null;
        return new TaxGroupData(id, name, taxAssociations, taxComponents);
    }
    public static TaxGroupData template(final Collection<TaxComponentData> taxComponents) {
        final Long id = null;
        final String name = null;
        final Collection<TaxGroupMappingsData> taxAssociations = null;
        return new TaxGroupData(id, name, taxAssociations, taxComponents);
    }
    public static TaxGroupData template(final TaxGroupData taxGroupData, final Collection<TaxComponentData> taxComponents) {
        return new TaxGroupData(taxGroupData.id, taxGroupData.name, taxGroupData.taxAssociations, taxComponents);
    }
    private TaxGroupData(final Long id, final String name, final Collection<TaxGroupMappingsData> taxAssociations,
            final Collection<TaxComponentData> taxComponents) {
        this.id = id;
        this.name = name;
        this.taxAssociations = taxAssociations;
        this.taxComponents = taxComponents;
    }
    public Collection<TaxGroupMappingsData> getTaxAssociations() {
        return this.taxAssociations;
    }
}
